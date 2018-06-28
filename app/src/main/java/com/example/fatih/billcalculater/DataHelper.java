package com.example.fatih.billcalculater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {
    public static final String Database_Name ="BillCalc";
    public static final String Table_Desk ="desk";
    public static final String Table_Menu ="menu";
    public static final String Table_Order ="ord";


    public static final int Database_Version =1;

    //Creating Database Tables
    public static final String Create_Menu ="CREATE TABLE IF NOT EXISTS "+Table_Menu+"(id INTEGER PRIMARY KEY AUTOINCREMENT,food_name STRING NOT NULL UNIQUE,price DOUBLE NOT NULL )";
    public static final String Create_Desk ="CREATE TABLE IF NOT EXISTS "+ Table_Desk +"(id INTEGER PRIMARY KEY AUTOINCREMENT, desk_no STRING NOT NULL UNIQUE )";
    public static final String Create_Order ="CREATE TABLE IF NOT EXISTS "+ Table_Order +"(id INTEGER PRIMARY KEY AUTOINCREMENT, desk_id STRING NOT NULL, food_id STRING NOT NULL,total DOUBLE NOT NULL)";

    public static final String Delete_Menu ="DROP TABLE IF EXISTS "+Table_Menu;
    public static final String Delete_Desk ="DROP TABLE IF EXISTS "+Table_Desk;
    public static final String Delete_Order ="DROP TABLE IF EXISTS "+Table_Order;

    public DataHelper(Context context){
        super(context,Database_Name,null,Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Desk);
        db.execSQL(Create_Menu);
        db.execSQL(Create_Order);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Delete_Order);
        db.execSQL(Delete_Desk);
        db.execSQL(Delete_Order);
        // Create tables again
        onCreate(db);
    }

    //Deleting All Desk orders from database which sent from Main Activity
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_Order,"desk_id=?",new String[]{id});
    }
    //Finding Selected food from list Which sent from Order Activity , then Deleting from Database table
    public Integer deleteOrder(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_Order,"id=?",new String[]{id});
    }

    //Adding Desk Numbers to Database which is sent from Main Activity
    public void insertDesk(String desk_no){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values= new ContentValues();
            values.put("desk_no",desk_no);
            db.insert(Table_Desk,null,values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    //Adding Food Names to Database which is sent from Main Activity
    public void insertMenu(String food_name,String price){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values= new ContentValues();
            values.put("food_name",food_name);
            values.put("price",price);
            db.insert(Table_Menu,null,values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    //Adding Order to Database which is sent from Main Activity
    public boolean AddData(String Masa,String Yemek,String Quantity){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("desk_id",Masa);

        Cursor cursor = db.query("menu",null,"food_name=?",new String[]{Yemek},null,null,null);
        cursor.moveToFirst();
        Double price = cursor.getDouble(cursor.getColumnIndex("price"));
        cursor.close();
        int quan=Integer.parseInt(Quantity);
        price*=quan;
        String price1=String.valueOf(price);
        contentValues.put("total",price1);

        contentValues.put("food_id",Quantity+" X "+Yemek+" (Price: "+price+"€)");

        long result=db.insert(Table_Order,null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    //Taking All Desk numbers from database table to list view
    public ArrayList<String> getAllDesk(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selecQuery = "SELECT * FROM "+ Table_Desk;
            Cursor cursor = db.rawQuery(selecQuery,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String desk_no = cursor.getString(cursor.getColumnIndex("desk_no"));
                    list.add(desk_no);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    //Taking All Food names from database table to list view
    public ArrayList<String>getAllMenu(){
        ArrayList<String> listmenu = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selecQuery = "SELECT * FROM "+Table_Menu;
            Cursor c = db.rawQuery(selecQuery,null);
            if (c.getCount()>0){
                while (c.moveToNext()){
                    String food_name = c.getString(c.getColumnIndex("food_name"));
                    double price = c.getDouble(c.getColumnIndex("price"));
                    listmenu.add(food_name);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return listmenu;
    }

    //Taking All Orders from database table to list view
    public ArrayList<String>getAllOrder(String Desk){
        ArrayList<String> listorder = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selecQuery = "SELECT * FROM "+Table_Order;
            Cursor c = db.query("ord",null,"desk_id=?",new String[]{Desk},null,null,null);
            if (c.getCount()>0){
                while (c.moveToNext()){
                    String food_name = c.getString(c.getColumnIndex("food_id"));
                    String id = c.getString(c.getColumnIndex("id"));

                    listorder.add(id+" - "+food_name);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return listorder;
    }

    //Finding Price of selected food
    public String findPrice(String food){
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query("menu",null,"food_name=?",new String[]{food},null,null,null);
        cursor.moveToFirst();
        String price = cursor.getString(cursor.getColumnIndex("price"));
        cursor.close();

        db.endTransaction();
        db.close();
        return price;
    }


    //Finding Total Price Of Desk
    public double findDeskTotal(String desk){
        double total=0,variable=0;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selecQuery = "SELECT * FROM "+Table_Order;
            Cursor c = db.query("ord",null,"desk_id=?",new String[]{desk},null,null,null);
            if (c.getCount()>0){
                while (c.moveToNext()){
                    variable=c.getDouble(c.getColumnIndex("total"));
                    total+=variable;
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return total;
    }
}
