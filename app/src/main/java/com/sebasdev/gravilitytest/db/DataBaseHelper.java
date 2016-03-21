package com.sebasdev.gravilitytest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by m4605 on 20/03/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "appsdb";
    protected static final int DATABASE_VERSION = 1;
    protected static final String APP_TABLE_NAME = "apps";
    protected static final String CATEGORY_TABLE_NAME = "category";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table apps
        db.execSQL("create table " + APP_TABLE_NAME + "(id integer primary key autoincrement," +
                "name text not null," +
                "author text not null," +
                "price text not null," +
                "description text not null," +
                "image text not null," +
                "image_bin blob not null)"); // TODO: 20/03/16 create field for relationship

        // create table category
        db.execSQL("create table " + CATEGORY_TABLE_NAME + "(id integer primary key, label text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables...
        db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE_NAME);
        // ...and create new table
        onCreate(db);
    }
}
