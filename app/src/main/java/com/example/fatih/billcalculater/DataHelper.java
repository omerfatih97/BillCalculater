package com.example.fatih.billcalculater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {
    public static final String Database_Name ="BillCalc";
    public static final String Table_Desk ="desk";
    public static final String Table_Menu ="menu";
    public static final String Table_Order ="ord";
    public static double top=0;

    public static final int Database_Version =1;

    public static final String Create_Menu ="CREATE TABLE IF NOT EXISTS "+Table_Menu+"(id INTEGER PRIMARY KEY AUTOINCREMENT,food_name STRING NOT NULL UNIQUE,price DOUBLE NOT NULL )";
    public static final String Create_Desk ="CREATE TABLE IF NOT EXISTS "+ Table_Desk +"(id INTEGER PRIMARY KEY AUTOINCREMENT, desk_no STRING NOT NULL UNIQUE )";
    public static final String Create_Order ="CREATE TABLE IF NOT EXISTS "+ Table_Order +"(id INTEGER PRIMARY KEY AUTOINCREMENT, desk_id INTEGER NOT NULL, food_id INTEGER NOT NULL )";

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
        db.execSQL(Delete_Desk);
        db.execSQL(Delete_Menu);
        db.execSQL(Delete_Order);
        onCreate(db);
    }

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

    public void insertOrder(int desk_id, String food_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values= new ContentValues();
            values.put("food_id",food_id);
            values.put("desk_id",desk_id);
            db.insert(Table_Order,null,values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

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

    public ArrayList<String> getAllOrder(){
        ArrayList<String> listorder = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {

            String selecQuery = "SELECT * FROM "+ Table_Desk;
            Cursor cursor = db.rawQuery(selecQuery,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    int desk_id = cursor.getInt(cursor.getColumnIndex("desk_no"));
                    listorder.add(String.valueOf(desk_id));
                }
            }
            selecQuery = "SELECT * FROM "+Table_Menu;
            Cursor c = db.rawQuery(selecQuery,null);
            if (c.getCount()>0){
                while (c.moveToNext()){
                    String food_id = c.getString(c.getColumnIndex("food_name"));
                    // double price = c.getDouble(c.getColumnIndex("price"));
                    listorder.add(food_id);
                    // listorder.add(String.valueOf(price));
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
    public String findPrice(String food){
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query("menu",null,"food_name=?",new String[]{food},null,null,null);
        cursor.moveToFirst();
        String price = cursor.getString(cursor.getColumnIndex("price"));
        cursor.close();

        return price;
    }

}
