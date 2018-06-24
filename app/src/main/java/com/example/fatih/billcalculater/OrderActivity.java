package com.example.fatih.billcalculater;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class OrderActivity extends AppCompatActivity {

    DataHelper dataHelper = new DataHelper(this);


    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

        Double Price=Double.parseDouble(intent.getStringExtra(MainActivity.Order_PRICE));
        String Desk=intent.getStringExtra(MainActivity.DeskPos);

        int quantity=Integer.parseInt(intent.getStringExtra(MainActivity.Quantity));
        Price*=quantity;

        final ArrayList<String>arrayList1=new ArrayList<String>();

        lv=(ListView)findViewById(R.id.listView_lv);


                    ArrayList<String> listOrder= dataHelper.getAllOrder();
                    ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);
                    lv.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    Toast.makeText(OrderActivity.this,"Başarı",Toast.LENGTH_LONG).show();



        TextView price= (TextView) findViewById(R.id.textViewPrice);
        price.setText(Price+" €");




    }


}
