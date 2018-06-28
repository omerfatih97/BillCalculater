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
import java.util.List;

import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    DataHelper dataHelper = new DataHelper(this);
    List<String> list = new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

        String desk=intent.getStringExtra(MainActivity.DeskPos);

        //Writing Selected Desk Tittle
        TextView orderList=(TextView)findViewById(R.id.Order_List);
        orderList.setText(""+desk+" Order List");

        Double Price=dataHelper.findDeskTotal(desk);        //Finding total bill of Desk

        //Listing All Orders of Selected Desk
        lv=(ListView)findViewById(R.id.listView_lv);
        final ArrayList<String> listOrder= dataHelper.getAllOrder(desk);        //Getting Orders From Database
        final ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);    //Putting Orders to List
        lv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        //Setting Delete button for delete the specific Order From List
        Button delBut=(Button)findViewById(R.id.orderDeleteBut);
        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText idtext= (EditText)findViewById(R.id.editTextDelID);
                String id= idtext.getText().toString();

                //Checking if the id column filled
                if(id.trim().isEmpty()){
                    idtext.setError("Id column should be filled. It can't be empty!!");
                }
                else
                {
                    int res=delData(id);    //Deleting Specific Order from Orderlist in Database
                    if (res==1)
                        Toast.makeText(OrderActivity.this,"Succesfully Deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(OrderActivity.this,"Wrong",Toast.LENGTH_LONG).show();
                }
            }

        });

        TextView price= (TextView) findViewById(R.id.textViewPrice);
        price.setText(Price+" €");      //Showing Total Price of Desk


    }

    //Delete function and refresh the page view
    public int delData(String id){

        Intent intent = getIntent();
        String desk=intent.getStringExtra(MainActivity.DeskPos);
        int res =dataHelper.deleteOrder(id);
        final ArrayList<String> listOrder= dataHelper.getAllOrder(desk);
        final ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOrder);
        lv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        Double Price=dataHelper.findDeskTotal(desk);
        TextView price= (TextView) findViewById(R.id.textViewPrice);
        price.setText(Price+" €");

        return res;
    }


}
