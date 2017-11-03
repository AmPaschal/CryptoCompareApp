package amusuo.compaschal.facebook.www.cryptocompare;

/**
 * Created by AMUSUO PASCHAL on 27/10/2017.
 */

public class CompareCurrency {

    private String fromCurrency;
    private String toCurrency;
    private double conversionRate;

    public CompareCurrency(String fCurrency, String tCurrency, double convRates){
        fromCurrency = fCurrency;
        toCurrency = tCurrency;
        conversionRate = convRates;
    }

    public String getFromCurrency(){return fromCurrency;}

    public double getConversionRate(){return conversionRate;}

    public String getToCurrency(){return toCurrency;}


}
