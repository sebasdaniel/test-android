package com.sebasdev.gravilitytest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by m4605 on 20/03/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "appsdb";
    protected static final int DATABASE_VERSION = 2;
    protected static final String APP_TABLE_NAME = "apps";
    protected static final String CATEGORY_TABLE_NAME = "category";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Set support for table relations
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys = ON");
            }
        }
    }

    /**
     * Create the DB tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table category
        db.execSQL("create table " + CATEGORY_TABLE_NAME + "(id INTEGER PRIMARY KEY, label TEXT NOT NULL)");

        // create table apps
        db.execSQL("CREATE TABLE " + APP_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "price TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "image_bitmap BLOB NOT NULL," +
                "category INTEGER NOT NULL," +
                "FOREIGN KEY(category) REFERENCES " + CATEGORY_TABLE_NAME + "(id))");
    }

    /**
     * When upgrade the schema recreate the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE_NAME);
        // create new tables
        onCreate(db);
    }
}
