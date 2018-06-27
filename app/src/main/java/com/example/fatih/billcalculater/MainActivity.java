package com.example.fatih.billcalculater;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    public static final String Order_Name ="com.example.fatih.billcalculater.ordername";
    public static final String Order_PRICE ="com.example.fatih.billcalculater.orderprice";
    public static final String Quantity = "com.example.fatih.billcalculater.quantity";
    public static final String MenuPos = "com.example.fatih.billcalculater.menupos";
    public static final String DeskPos = "com.example.fatih.billcalculater.deskpos";



    DataHelper dataHelper = new DataHelper(this);
    SQLiteDatabase db;

    String[] orderDesk =new String[100];
    String gecici=new String();

    int i=0,count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this, OrderActivity.class);

        // final DataHelper dataHelper = new DataHelper(this);
        dataHelper.insertDesk("Desk 1");
        dataHelper.insertDesk("Desk 2");
        dataHelper.insertDesk("Desk 3");

        dataHelper.insertMenu("Fish", String.valueOf(5));
        dataHelper.insertMenu("Hamburger", String.valueOf(4));
        dataHelper.insertMenu("Pizza", String.valueOf(3.5));
        dataHelper.insertMenu("Doner", String.valueOf(3));
        dataHelper.insertMenu("Toast", String.valueOf(2));

        ArrayList<String> listMenu = dataHelper.getAllMenu();
        final Spinner spMenu = (Spinner) findViewById(R.id.spinnerMenu);
        ArrayAdapter<String> adapterMenu = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.Mtxt, listMenu);

        ArrayList<String> listDesk = dataHelper.getAllDesk();
        final Spinner sp = (Spinner) findViewById(R.id.spinnerDesk);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.Dtxt, listDesk);

        sp.setAdapter(adapter);
        spMenu.setAdapter(adapterMenu);

        //Find Price for Main Screen
        final TextView Screenprice = (TextView) findViewById(R.id.textViewPrice);

        spMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                // Notify the selected item text
                String FPrice1 = dataHelper.findPrice(selectedItemText);

                Screenprice.setText(FPrice1.toString());

                intent.putExtra(MenuPos, selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Desk_id = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                intent.putExtra(DeskPos, Desk_id);
                orderDesk[0] = Desk_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button addBtn = (Button) findViewById(R.id.buttonAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            String gecici = new String();

            @Override
            public void onClick(View v) {


                final EditText quantity = (EditText) findViewById(R.id.editTextQuantity);
                final String Quantity1 = quantity.getText().toString();

                if(Quantity1.trim().isEmpty())
                    quantity.setError("Enter Quantity please!!!");
                else {
                    intent.putExtra(Quantity, Quantity1);
                    Double say = Double.parseDouble(intent.getStringExtra(MainActivity.Quantity));

                    final TextView order = (TextView) findViewById(R.id.Mtxt);
                    TextView price = (TextView) findViewById(R.id.Mtxt);

                    final String ordername = order.getText().toString();
                    String orderprice1 = price.getText().toString();

                    String orderprice = dataHelper.findPrice(orderprice1);

                    say *= Double.parseDouble(String.valueOf(orderprice));
                    orderprice = say.toString();

                    if (orderDesk[i] != null) {
                        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String Desk_id = (String) parent.getItemAtPosition(position);
                                // Notify the selected item text
                                if (Desk_id != null)
                                    orderDesk[i] = Desk_id;
                                else if (orderDesk[i] == null && Desk_id == null)
                                    orderDesk[i] = orderDesk[i - 1];

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        if (orderDesk[i] != null) {
                            gecici = orderDesk[i];
                        } else
                            orderDesk[i] = gecici;


                        gecici = orderDesk[i];
                        //Toast.makeText(MainActivity.this," "+gecici,Toast.LENGTH_LONG).show();
                        AddData(gecici, ordername, Quantity1);

                        intent.putExtra(DeskPos, orderDesk);
                        i++;
                    }


                    intent.putExtra(Order_Name, ordername);
                    intent.putExtra(Order_PRICE, orderprice);
                    intent.putExtra(Quantity, Quantity1);
                }



            }
        });

        Button delBtn = (Button) findViewById(R.id.buttonDel);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderDesk[i]!=null || count==0){
                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String Desk_id=(String)parent.getItemAtPosition(position);
                            // Notify the selected item text
                            if(Desk_id!=null)
                                orderDesk[i]=Desk_id;
                            else if(orderDesk[i]==null&&Desk_id==null)
                                orderDesk[i]=orderDesk[i-1];

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    if(orderDesk[i]!=null){
                        gecici=orderDesk[i];
                    }else
                        orderDesk[i]=gecici;


                    gecici=orderDesk[i];
                    //Toast.makeText(MainActivity.this,"Geldi ",Toast.LENGTH_LONG).show();
                    //dataHelper.deleteData(gecici);
                    delData(gecici);
                    i++;
                }
            }
        });

        //Show Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String Desk_id = (String) parent.getItemAtPosition(position);
                        // Notify the selected item text
                        intent.putExtra(DeskPos, Desk_id);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

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

    public void AddData(String newDesk,String newOrder,String quantity){
       boolean insertData=dataHelper.AddData( newDesk,newOrder,quantity);
        if (insertData==true){
            Toast.makeText(MainActivity.this,"Successfully  Added ",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this,"Wrong",Toast.LENGTH_LONG).show();
        }
    }
            public void delData(String newDesk){
                int insert= dataHelper.deleteData(newDesk);
                if (insert==1){
                    Toast.makeText(MainActivity.this,"("+newDesk+") Data's Successfully deleted!!!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Wrong",Toast.LENGTH_LONG).show();
                }
            }

}

