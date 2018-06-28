package com.example.fatih.billcalculater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        Double Price=dataHelper.findDeskTotal(Desk);

        final ArrayList<String>arrayList1=new ArrayList<String>();

        lv=(ListView)findViewById(R.id.listView_lv);
        Button delBut=(Button)findViewById(R.id.orderDeleteBut);


                    ArrayList<String> listOrder= dataHelper.getAllOrder(Desk);
                    ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);
                    lv.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();

        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText idtext= (EditText)findViewById(R.id.editTextDelID);

                String id= idtext.getText().toString();

                if(id.trim().isEmpty()){
                    idtext.setError("Id coloumn should be filled. It can't be empty!!");
                }
                else
                {
                    int res=delData(id);
                    if (res==1)
                        Toast.makeText(OrderActivity.this,"Succesfully Deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(OrderActivity.this,"Wrong!!!",Toast.LENGTH_LONG).show();
                }
            }

        });

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
