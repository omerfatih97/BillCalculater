package com.example.fatih.billcalculater;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    public static final String Order_Name ="com.example.fatih.billcalculater.ordername";
    public static final String Order_PRICE ="com.example.fatih.billcalculater.orderprice";
    public static final String Quantity = "com.example.fatih.billcalculater.quantity";
    public static final String MenuPos = "com.example.fatih.billcalculater.menupos";
    public static final String DeskPos = "com.example.fatih.billcalculater.deskpos";

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

    //Find Price for Main Screen
       final TextView Screenprice=(TextView) findViewById(R.id.textViewPrice);

        spMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                long Menu_id= parent.getItemIdAtPosition(position);
                // Notify the selected item text
                String FPrice1=dataHelper.findPrice(selectedItemText);

                Screenprice.setText(FPrice1.toString());

                intent.putExtra(MenuPos,Integer.valueOf(String.valueOf(Menu_id)));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long Desk_id= parent.getItemIdAtPosition(position);
                // Notify the selected item text

                intent.putExtra(DeskPos,Integer.valueOf(String.valueOf(Desk_id)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Show Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               EditText quantity = (EditText)findViewById(R.id.editTextQuantity);

               String Quantity1= quantity.getText().toString();
               intent.putExtra(Quantity,Quantity1);

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
