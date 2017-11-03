package amusuo.compaschal.facebook.www.cryptocompare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AMUSUO PASCHAL on 27/10/2017.
 */

public class CompareCurrencyAdapter extends ArrayAdapter<CompareCurrency> {

    public CompareCurrencyAdapter(@NonNull Context context, ArrayList<CompareCurrency> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemViews = convertView;

        if (itemViews == null){
            itemViews = LayoutInflater.from(getContext()).inflate(R.layout.comparecurrency_list_item, parent, false);
        }

        CompareCurrency compareCurrency = (CompareCurrency)getItem(position);

        String compareCurrencyString = compareCurrency.getFromCurrency() + " 1 : "
                + compareCurrency.getConversionRate() +" "+ compareCurrency.getToCurrency();

        TextView crypto = (TextView)itemViews.findViewById(R.id.crypto);
        crypto.setText(compareCurrency.getFromCurrency());
        TextView currencyValue = (TextView)itemViews.findViewById(R.id.currencyValue);
        currencyValue.setText(String.valueOf(compareCurrency.getConversionRate()));
        TextView currencyName = (TextView)itemViews.findViewById(R.id.currencyName);
        String currencyShort = compareCurrency.getToCurrency();
        currencyName.setText(getCurrencyFull(currencyShort));
        return itemViews;
    }


    private String getCurrencyFull(String currencyShort) {
        String currencyFull = null;
        switch(currencyShort){
            case "USD":
                currencyFull = "US Dollars";
                break;
            case "NGN":
                currencyFull = "Nigerian Naira";
                break;
            case "EUR":
                currencyFull = "Euro";
                break;
            case "AED":
                currencyFull = "UAE Dirhan";
                break;
            case "ARS":
                currencyFull = "Argentina Peso";
                break;
            case "BRL":
                currencyFull = "Brazil Real";
                break;
            case "CHF":
                currencyFull = "Switzerland Franc";
                break;
            case "CAD":
                currencyFull = "Canada Dollars";
                break;
            case "EGP":
                currencyFull = "Egypt Pound";
                break;
            case "GBP":
                currencyFull = "British Pound";
                break;
            case "RUB":
                currencyFull = "Russia Ruble";
                break;
            case "RWF":
                currencyFull = "Rwanda Frank";
                break;
            //NGN,USD,EUR,AED,ARS,BRL,CHF,CAD,EGP,GBP,RUB,RWF,UAH,ZAR,CZK,INR,AUD,MYR,JPY,CNY"
            case "UAH":
                currencyFull = "Ukraine Hryvnia";
                break;
            case "ZAR":
                currencyFull = "South Africa Rand";
                break;
            case "CZK":
                currencyFull = "Czech Koruna";
                break;
            case "INR":
                currencyFull = "India Rupee";
                break;
            case "AUD":
                currencyFull = "Australia Dollar";
                break;
            case "MYR":
                currencyFull = "Malaysia Ringgit";
                break;
            case "JPY":
                currencyFull = "Japan Yen";
                break;
            case "CNY":
                currencyFull = "China Yuan";
                break;
            default:
                currencyFull = null;
        }
        return currencyFull;
    }
}
