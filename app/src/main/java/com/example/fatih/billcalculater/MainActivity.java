package com.example.fatih.billcalculater;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String Order_Name ="com.example.fatih.billcalculater.ordername";
    public static final String Order_PRICE ="com.example.fatih.billcalculater.orderprice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db;
        final Intent intent = new Intent(this,OrderActivity.class);

        final DataHelper dataHelper = new DataHelper(this);
        dataHelper.insertDesk("Desk 1");
        dataHelper.insertDesk("Desk 2");
        dataHelper.insertDesk("Desk 3");

        dataHelper.insertMenu("Fish", String.valueOf(5));
        dataHelper.insertMenu("Hamburger", String.valueOf(4));
        dataHelper.insertMenu("Pizza", String.valueOf(3.5));
        dataHelper.insertMenu("Doner", String.valueOf(3));
        dataHelper.insertMenu("Toast", String.valueOf(2));


        ArrayList<String> listMenu = dataHelper.getAllMenu();
        Spinner spMenu = (Spinner)findViewById(R.id.spinnerMenu);
        ArrayAdapter<String> adapterMenu=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.Mtxt,listMenu);

        ArrayList<String>listDesk = dataHelper.getAllDesk();
        Spinner sp = (Spinner)findViewById(R.id.spinnerDesk);
        final ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.Dtxt,listDesk);

        sp.setAdapter(adapter);
        spMenu.setAdapter(adapterMenu);


      String FPrice=spMenu.getSelectedItem().toString();

       FPrice=dataHelper.findPrice(FPrice);
       TextView Screenprice=(TextView) findViewById(R.id.textViewPrice);

       Screenprice.setText(FPrice.toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



                TextView order=(TextView) findViewById(R.id.Mtxt);
                TextView price=(TextView) findViewById(R.id.Mtxt);


                String ordername=order.getText().toString();
                String orderprice1=price.getText().toString();

                String orderprice=dataHelper.findPrice(orderprice1);

                intent.putExtra(Order_Name,ordername);
                intent.putExtra(Order_PRICE,orderprice);

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
