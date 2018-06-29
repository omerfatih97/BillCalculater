package com.example.fatih.billcalculater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class OrderContentProvider extends ContentProvider {
    DataHelper dataHelper;

    private static final String AUTHORITY = "com.example.fatih.billcalculater";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    SQLiteOpenHelper sqLiteOpenHelper;

    static UriMatcher uriMatcher=new UriMatcher(-1);

    static {
        uriMatcher.addURI("com.example.fatih.billcalculater","ord",1);
    }

    @Override
    public boolean onCreate() {
        dataHelper=new DataHelper(getContext().getApplicationContext());
        dataHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder) {
        Cursor cursor=null;

        switch (uriMatcher.match(uri)){
            case 1:
                cursor=dataHelper.query(selectionArgs[0]);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
