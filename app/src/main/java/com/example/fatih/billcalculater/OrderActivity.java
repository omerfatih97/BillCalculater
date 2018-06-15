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

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();


        String name = intent.getStringExtra(MainActivity.MenuPos);
        Double Price=(Double.parseDouble( intent.getStringExtra(MainActivity.Order_PRICE)));
        Double Quantity=(Double.parseDouble(intent.getStringExtra(MainActivity.Quantity)));

        Price*=Quantity;

        lv=(ListView)findViewById(R.id.listView_lv);

        final ArrayList<String>arrayList1=new ArrayList<String>();

        for (int i=0;i<Quantity;i++){
            arrayList1.add(name);
        }

        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList1);
        lv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        TextView price= (TextView) findViewById(R.id.textViewPrice);
        TextView order = (TextView)findViewById(R.id.textViewTotal);

        int quantity=(Integer.parseInt(intent.getStringExtra(MainActivity.Quantity)));


        order.setText(quantity+" X "+name);
        price.setText(Price+" €");

    }
}
