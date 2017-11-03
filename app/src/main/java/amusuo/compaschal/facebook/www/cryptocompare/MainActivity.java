package amusuo.compaschal.facebook.www.cryptocompare;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CompareCurrency>>{


    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int EARHQUAKE_LOADED_ID = 1;
    private CompareCurrencyAdapter compareCurrencyAdapter;

    private final String conversionRatesUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,USD,EUR,AED,ARS,BRL,CHF,CAD,EGP,GBP,RUB,RWF,UAH,ZAR,CZK,INR,AUD,MYR,JPY,CNY";
    public static final String TO_CURRENCY = "Currency 2";
    public static final String FROM_CURRENCY = "Currency 1";
    public static final String CONVERSION_RATE = "Conversion rate";

    String to_currency;
    String from_currency;
    double conversion_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView)findViewById(R.id.list);
        compareCurrencyAdapter = new CompareCurrencyAdapter(this, new ArrayList<CompareCurrency>());
        listView.setAdapter(compareCurrencyAdapter);

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARHQUAKE_LOADED_ID, null, this);
            Log.i(LOG_TAG, "Loader manager initialized");
        }
        else{

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CompareCurrency compareCurrency = compareCurrencyAdapter.getItem(position);
                to_currency = compareCurrency.getToCurrency();
                from_currency = compareCurrency.getFromCurrency();
                conversion_rate = compareCurrency.getConversionRate();

                openConversionActivity();
            }
        });

    }

    private void openConversionActivity() {
        Intent intent = new Intent(this, ConversionActivity.class);
        intent.putExtra(TO_CURRENCY, to_currency);
        intent.putExtra(FROM_CURRENCY, from_currency);
        intent.putExtra(CONVERSION_RATE, conversion_rate);

        startActivity(intent);
    }


    @Override
    public Loader<ArrayList<CompareCurrency>> onCreateLoader(int i, Bundle bundle) {
        return new CryptoCompareLoader(this, conversionRatesUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CompareCurrency>> loader, ArrayList<CompareCurrency> compareCurrencies) {
        compareCurrencyAdapter.clear();

        if (compareCurrencies != null && !compareCurrencies.isEmpty()){
            compareCurrencyAdapter.addAll(compareCurrencies);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CompareCurrency>> loader) {
        compareCurrencyAdapter.clear();
    }
}

