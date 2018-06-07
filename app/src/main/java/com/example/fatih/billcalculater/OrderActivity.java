package com.example.fatih.billcalculater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    DataHelper db=new DataHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();


        String name = intent.getStringExtra(MainActivity.Order_Name);
        Double Price=(Double.parseDouble( intent.getStringExtra(MainActivity.Order_PRICE)));
        Double Quantity=(Double.parseDouble(intent.getStringExtra(MainActivity.Quantity)));

        Price*=Quantity;


        TextView price= (TextView) findViewById(R.id.textViewMasano);
        TextView order = (TextView)findViewById(R.id.textViewTotal);

        int quantity=(Integer.parseInt(intent.getStringExtra(MainActivity.Quantity)));

        order.setText(quantity+" X "+name);
        price.setText(Price+" €");

    }
}
