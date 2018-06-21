package com.example.fatih.billcalculater;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class OrderActivity extends AppCompatActivity {
/*
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayAdapter adapter = new ArrayAdapter(this, list, R.layout.activity_order, new String[] {
                "Desk", "order"}, new int[] {
                R.id.Dtxt, R.id.Mtxt  });

        addData();
        setListAdapter(adapter);
    }

    public void addData() {
        Intent intent = getIntent();
        String name=intent.getStringExtra(MainActivity.Order_Name);
        String desk=intent.getStringExtra(MainActivity.DeskPos);
        if(name=="Hamburger"){
            if(desk=="Desk 1"){
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("Desk", "Desk 1"); // Change to a method for getting current date
        temp.put("order", getIntent().getExtras().getString("Order_Name"));

        list.add(temp);}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/


    DataHelper dataHelper = new DataHelper(this);


    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

       String name = intent.getStringExtra(MainActivity.MenuPos);
        Double Price=(Double.parseDouble( intent.getStringExtra(MainActivity.Order_PRICE)));
        Double Quantity=(Double.parseDouble(intent.getStringExtra(MainActivity.Quantity)));
        int quantity=(Integer.parseInt(intent.getStringExtra(MainActivity.Quantity)));
        Price*=Quantity;


        final ArrayList<String>arrayList1=new ArrayList<String>();
        lv=(ListView)findViewById(R.id.listView_lv);

        for (int i=0;i<Quantity;i++){
            arrayList1.add(getIntent().getExtras().getString("com.example.fatih.billcalculater.ordername"));
        }

        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList1);
        lv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        TextView price= (TextView) findViewById(R.id.textViewPrice);
        TextView order = (TextView)findViewById(R.id.textViewTotal);

      //  ArrayList<String> listMenu = dataHelper.getAllOrder();

       // ArrayAdapter<String> adapterOrd=new ArrayAdapter<String>(this,R.layout.activity_order,R.id.textViewTotal,listMenu);
        //lv.setAdapter(adapterOrd);


        order.setText(quantity+" X "+name);
        price.setText(Price+" €");
 /*
        lv=(ListView)findViewById(R.id.listView_lv);

        ArrayList<String>orderList=new ArrayList<>();
        Cursor data=dataHelper.getListOrder();

        if(data.getCount()==0){
            Toast.makeText(OrderActivity.this,"Database Empty",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()){
                orderList.add(data.getString(1));
                ListAdapter listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 ,orderList);
                lv.setAdapter(listAdapter);
            }
        }*/




    }

}
