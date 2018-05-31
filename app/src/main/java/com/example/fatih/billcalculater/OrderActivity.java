package com.example.fatih.billcalculater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

        String Total = intent.getStringExtra(MainActivity.Order_Name);
        String Price=(String.valueOf( intent.getStringExtra(MainActivity.Order_PRICE)));

        TextView price= (TextView) findViewById(R.id.textViewMasano);

        TextView order = (TextView)findViewById(R.id.textViewTotal);

        price.setText(Price.toString()+" €");
        order.setText(Total);
    }
}
