package com.sebasdev.gravilitytest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by m4605 on 20/03/16.
 */
public class DataBaseConnexion {

    private  DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    private ArrayList<App> apps = null;
    private ArrayList<Category> categories = null;

    public DataBaseConnexion(Context context) {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Recreate database for sync it with the new values
     */
    public void syncDataBase(ArrayList<App> apps, ArrayList<Category> categories) {

        // drop and create database
        dbHelper.onUpgrade(database, 0, 0);

        // save categories
        for (Category category : categories) {

            ContentValues values = new ContentValues();

            values.put("id", category.getId());
            values.put("label", category.getLabel());

            database.insert(dbHelper.CATEGORY_TABLE_NAME, null, values);// TODO: 21/03/16 create log in case of failure
        }

        //save apps
        for (App app : apps) {

            ContentValues values = new ContentValues();

            values.put("name", app.getName());
            values.put("author", app.getAuthor());
            values.put("price", app.getPrice());
            values.put("description", app.getDescription());
            values.put("image", app.getImage());
            values.put("image_bitmap", getImageBytes(app.getImageBitmap()));
            values.put("category", app.getCategory().getId());

            database.insert(dbHelper.APP_TABLE_NAME, null, values);
        }
    }

    /**
     * Extract data from database and push it in local variables
     */
    public void getData() {

        // get categories
        String query = "select * from " + dbHelper.CATEGORY_TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        categories = new ArrayList<>();

        while (cursor.moveToNext()) {

            categories.add(new Category(cursor.getInt(0), cursor.getString(1)));
        }

        // get apps
        query = "select * from " + dbHelper.APP_TABLE_NAME;
        cursor = database.rawQuery(query, null);

        apps = new ArrayList<>();

        while (cursor.moveToNext()) {

            App app = new App();

            app.setName(cursor.getString(1));
            app.setAuthor(cursor.getString(2));
            app.setPrice(cursor.getString(3));
            app.setDescription(cursor.getString(4));
            app.setImage(cursor.getString(5));
            app.setImageBitmap(getImageBitmap(cursor.getBlob(6)));
            app.setCategory(getCategory(cursor.getInt(7)));

            apps.add(app);
        }
    }

    /**
     * Return the list of apps
     */
    public ArrayList<App> getApps() {

        if (apps == null)
            getData();

        return apps;
    }

    /**
     * Return the list of categories
     */
    public ArrayList<Category> getCategories() {

        if (categories == null)
            getData();

        return categories;
    }

    /**
     * Obtain category with the param id from local variable
     */
    private Category getCategory(int id) {

        for (Category category: categories) {
            if (category.getId() == id)
                return category;
        }
        return null;
    }

    /**
     * Convert image from bitmap to byte array
     */
    private static byte[] getImageBytes(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * Convert image from byte array to bitmap
     */
    private static Bitmap getImageBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
