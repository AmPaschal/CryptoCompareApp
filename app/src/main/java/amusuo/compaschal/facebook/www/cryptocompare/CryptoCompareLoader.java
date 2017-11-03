package amusuo.compaschal.facebook.www.cryptocompare;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by AMUSUO PASCHAL on 25/10/2017.
 */

public class CryptoCompareLoader extends AsyncTaskLoader<ArrayList<CompareCurrency>> {

    private String mUrl = null;
    public CryptoCompareLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<CompareCurrency> loadInBackground() {

        ArrayList conversionRates = QueryUtils.fetchConversionRates(mUrl);
        return conversionRates;
    }
}
