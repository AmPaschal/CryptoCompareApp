package amusuo.compaschal.facebook.www.cryptocompare;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();



    /**
     * Query the USGS dataset and return a list of Integer objects.
     */
    public static ArrayList<CompareCurrency> fetchConversionRates(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link EarthQuake}s
        ArrayList<CompareCurrency> conversionRates = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link EarthQuake}s
        return conversionRates;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    /**
     * Returns new URL object from the given string URL.
     */
    static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            Log.e(LOG_TAG, e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of integer objects that has been built up from
     * parsing the given JSON response.
     */
    static ArrayList<CompareCurrency> extractFeatureFromJson(String cryptocompareJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(cryptocompareJSON)) {
            return null;
        }

        ArrayList<CompareCurrency> compare = new ArrayList<>();
        String[] toCurrencyString = new String[]
                {"USD","EUR","AED","ARS","BRL","CHF","CAD","EGP","GBP","RUB","RWF","UAH","ZAR","CZK","INR","AUD","MYR","JPY","CNY"};
        ArrayList<String> toCurrencies = new ArrayList<>(Arrays.asList(toCurrencyString));
        String[] fromCurrencyString = new String[]{"ETH","BTC"};
        ArrayList<String> fromCurrencies = new ArrayList<>(Arrays.asList(fromCurrencyString));

        try {
            JSONObject baseJsonObject = new JSONObject(cryptocompareJSON);
            for (int i = 0; i < fromCurrencies.size(); i++){
                JSONObject fromJsonObject = baseJsonObject.getJSONObject(fromCurrencies.get(i));

                for (int j = 0; j < toCurrencies.size(); j++){
                    double toCurrency = fromJsonObject.getDouble(toCurrencies.get(j));
                    CompareCurrency compareCurrency = new CompareCurrency(fromCurrencies.get(i), toCurrencies.get(j), toCurrency);
                    compare.add(compareCurrency);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Cryptocompare", "Error parsing JSON");
        }
        return compare;


    }
}