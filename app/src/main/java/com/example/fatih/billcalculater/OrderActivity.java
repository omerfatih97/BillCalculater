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
        String Desk=intent.getStringExtra(MainActivity.DeskPos);
        TextView orderList=(TextView)findViewById(R.id.Order_List);
        orderList.setText(""+Desk+" Order List");
        Toast.makeText(OrderActivity.this," "+Desk,Toast.LENGTH_LONG).show();
        Double Price=dataHelper.findDesk(Desk);

        final ArrayList<String>arrayList1=new ArrayList<String>();

        lv=(ListView)findViewById(R.id.listView_lv);


                    ArrayList<String> listOrder= dataHelper.getAllOrder(Desk);
                    ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);
                    lv.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();


        TextView price= (TextView) findViewById(R.id.textViewPrice);
        price.setText(Price+" €");

    }

    public int delData(String id){
        Intent intent = getIntent();
        String desk=intent.getStringExtra(MainActivity.DeskPos);
        int res =dataHelper.deleteOrder(id);
        final ArrayList<String> listOrder= dataHelper.getAllOrder(desk);
        final ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);
        lv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        return res;
    }


}
