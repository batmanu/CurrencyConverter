package com.samcchan.currencyconverter;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    final String[] currency = {"USD", "Euro", "HKD", "Yuan", "Yen", "Dong"};
    public String top_currency = "USD";
    public float top_number = 0.0f;
    public float bottom_number = 0.0f;
    public String bottom_currency = "Euro";
    public static final String TAG = "MainActivity";
    String focusOn;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner topSpinner = findViewById(R.id.CurrencyFrom);
        Spinner bottomSpinner = findViewById(R.id.CurrencyTo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topSpinner.setAdapter(adapter);
        topSpinner.setSelection(0);
        bottomSpinner.setAdapter(adapter);
        bottomSpinner.setSelection(1);

        final EditText topNumber = findViewById(R.id.NumberFrom);
        final EditText bottomNumber = findViewById(R.id.NumberTo);

        topSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                top_currency = currency[i];
                if (top_number != 0.0f && bottom_number != 0.0f){
                    float to = convert(top_number, top_currency, bottom_currency);
                    String res = Float.toString(to);
                    bottomNumber.setText(res);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bottomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bottom_currency = currency[i];
                if (top_number != 0.0f && bottom_number != 0.0f){
                    float to = convert(bottom_number, bottom_currency, top_currency);
                    String res = Float.toString(to);
                    topNumber.setText(res);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        topNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusOn = "top";
                return false;
            }
        });

        bottomNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusOn = "bottom";
                return false;
            }
        });

        topNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                if(focusOn.equals("top")){
                    if(charSequence.length() != 0) {
                        float from = Float.parseFloat(charSequence.toString());
                        float to = convert(from, top_currency, bottom_currency);
                        top_number = from;
                        Log.i(TAG, "onTextChanged: top number is " + charSequence.toString());
                        bottom_number = to;
                        String res = Float.toString(to);
                        bottomNumber.setText(res);
                    } else {
                        top_number = 0.0f;
                        bottom_number = 0.0f;
                        bottomNumber.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        bottomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: bottom number");
                if(focusOn.equals("bottom")){
                    Log.i(TAG, "onTextChanged: " + charSequence);
                    if(charSequence.length() != 0) {
                        float from = Float.parseFloat(charSequence.toString());
                        float to = convert(from, bottom_currency, top_currency);
                        bottom_number = from;
                        top_number = to;
                        String res = Float.toString(to);
                        topNumber.setText(res);
                    } else {
                        top_number = 0.0f;
                        bottom_number = 0.0f;
                        topNumber.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public float convert(float from_value, String from_curr, String to_curr){
        if (from_curr.equals(to_curr)) {
            return from_value;
        } else if(from_curr.equals("USD")){
            switch (to_curr){
                case "Euro":
                    return ((float) (from_value * 0.82));
                case "HKD":
                    return ((float) (from_value * 7.85));
                case "Yuan":
                    return ((float) (from_value * 6.33));
                case "Yen":
                    return ((float) (from_value * 109.02));
                case "Dong":
                    return ((float) (from_value * 22763.50));
            }
        } else if(from_curr.equals("Euro")){
            switch (to_curr){
                case "USD":
                    return ((float) (from_value * 1.21));
                case "HKD":
                    return ((float) (from_value * 9.52));
                case "Yuan":
                    return ((float) (from_value * 7.68));
                case "Yen":
                    return ((float) (from_value * 132.3));
                case "Dong":
                    return ((float) (from_value * 27532.74));
            }
        } else if(from_curr.equals("HKD")){
            switch (to_curr){
                case "USD":
                    return ((float) (from_value * 0.13));
                case "Euro":
                    return ((float) (from_value * 0.11));
                case "Yuan":
                    return ((float) (from_value * 0.81));
                case "Yen":
                    return ((float) (from_value * 13.9));
                case "Dong":
                    return ((float) (from_value * 2892.29));
            }
        } else if(from_curr.equals("Yuan")){
            switch (to_curr){
                case "USD":
                    return ((float) (from_value * 0.16));
                case "Euro":
                    return ((float) (from_value * 0.13));
                case "HKD":
                    return ((float) (from_value * 1.24));
                case "Yen":
                    return ((float) (from_value * 17.27));
                case "Dong":
                    return ((float) (from_value * 3591.62));
            }
        } else if(from_curr.equals("Yen")){
            switch (to_curr){
                case "USD":
                    return ((float) (from_value * 0.0092));
                case "Euro":
                    return ((float) (from_value * 0.0076));
                case "HKD":
                    return ((float) (from_value * 0.072));
                case "Yuan":
                    return ((float) (from_value * 0.058));
                case "Dong":
                    return ((float) (from_value * 208.03));
            }
        } else if(from_curr.equals("Dong")){
            switch (to_curr){
                case "USD":
                    return ((float) (from_value * 0.000044));
                case "Euro":
                    return ((float) (from_value * 0.000036));
                case "HKD":
                    return ((float) (from_value * 0.00035));
                case "Yuan":
                    return ((float) (from_value * 0.00028));
                case "Yen":
                    return ((float) (from_value * 0.0048));
            }
        }
        return (float) 0.0;
    }
}