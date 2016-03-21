package com.sebasdev.gravilitytest.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;

import java.util.ArrayList;

/**
 * Created by m4605 on 20/03/16.
 */
public class DataBaseConnexion {

    private  DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public DataBaseConnexion(Context context) {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<App> getApps() {

        String query = "select * from " + dbHelper.APP_TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<App> apps = new ArrayList<>();

        while (cursor.moveToNext()) {

            App app = new App();

            app.setName(cursor.getString(1));
            app.setAuthor(cursor.getString(2));
            app.setPrice(cursor.getString(3));
            app.setDescription(cursor.getString(4));
            app.setImage(cursor.getString(5));
            // TODO: 20/03/16 set bitmap and category

            apps.add(app);
        }

        if (apps.size() > 0)
            return apps;
        // may return apps only
        return null;
    }

    public ArrayList<Category> getCategories() {

        String query = "select * from " + dbHelper.CATEGORY_TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<Category> categories = new ArrayList<>();

        while (cursor.moveToNext()) {

            categories.add(new Category(cursor.getInt(0), cursor.getString(1)));
        }

        if (categories.size() > 0)
            return categories;

        return null;
    }
}
