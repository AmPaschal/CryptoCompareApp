package amusuo.compaschal.facebook.www.cryptocompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AMUSUO PASCHAL on 30/10/2017.
 */

public class ConversionActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        Intent intent = getIntent();
        String fromCurrency = intent.getStringExtra(MainActivity.FROM_CURRENCY);
        String toCurrency = intent.getStringExtra(MainActivity.TO_CURRENCY);
        final double conversionRate = intent.getDoubleExtra(MainActivity.CONVERSION_RATE, 0);

        TextView toCurrencyTv = (TextView)findViewById(R.id.toCurrencyTv);
        toCurrencyTv.setText(toCurrency);
        TextView fromCurrencyTv = (TextView)findViewById(R.id.fromCurrencyTv);
        fromCurrencyTv.setText(fromCurrency);

        Button buttonConvert = (Button)findViewById(R.id.button);
        final EditText to_currencyValue = (EditText)findViewById(R.id.toCurrencyValue);
        final EditText from_currencyValue = (EditText)findViewById(R.id.fromCurrencyValue);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double crypto = Double.parseDouble(from_currencyValue.getText().toString().trim());
                double convertedValue = crypto * conversionRate;
                to_currencyValue.setText(String.valueOf(convertedValue));
            }
        });
    }
}

