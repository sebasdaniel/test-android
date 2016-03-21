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
     * Recreate database for sync it width the new values
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

    public ArrayList<App> getApps() {

        if (apps == null)
            getData();

        return apps;
    }

    public ArrayList<Category> getCategories() {

        if (categories == null)
            getData();

        return categories;
    }

    // retorna id de app
//    public long createApp(App app) {
//
//        ContentValues values = new ContentValues();
//
//        values.put("name", app.getName());
//        values.put("author", app.getAuthor());
//        values.put("price", app.getPrice());
//        values.put("description", app.getDescription());
//        values.put("image", app.getImage());
//        values.put("image_bitmap", getImageBytes(app.getImageBitmap()));
//        values.put("category", app.getCategory().getId());
//
//        return database.insert(dbHelper.APP_TABLE_NAME, null, values);
//    }

//    public ArrayList<App> getApps() {
//
//        String query = "select * from " + dbHelper.APP_TABLE_NAME;
//        Cursor cursor = database.rawQuery(query, null);
//
//        ArrayList<App> apps = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//
//            App app = new App();
//
//            app.setName(cursor.getString(1));
//            app.setAuthor(cursor.getString(2));
//            app.setPrice(cursor.getString(3));
//            app.setDescription(cursor.getString(4));
//            app.setImage(cursor.getString(5));
//            app.setImageBitmap(getImageBitmap(cursor.getBlob(6)));
//            app.setCategory(getCategory2(cursor.getInt(7)));
//
//            apps.add(app);
//        }
//
//        if (apps.size() > 0)
//            return apps;
//        // may return apps only
//        return null;
//    }

//    public ArrayList<Category> getCategories() {
//
//        String query = "select * from " + dbHelper.CATEGORY_TABLE_NAME;
//        Cursor cursor = database.rawQuery(query, null);
//
//        ArrayList<Category> categories = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//
//            categories.add(new Category(cursor.getInt(0), cursor.getString(1)));
//        }
//
//        if (categories.size() > 0)
//            return categories;
//
//        return null;
//    }

//    public Category getCategory(int id) {
//
//        String query = "select * from " + dbHelper.CATEGORY_TABLE_NAME + " where id=?";
//        Cursor cursor = database.rawQuery(query, new String[]{""+id});
//
//        ArrayList<Category> categories = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//
//            Category category = new Category(cursor.getInt(0), cursor.getString(1));
//            return category;
//        }
//
//        return null;
//    }

    public Category getCategory(int id) {

        for (Category category: categories) {
            if (category.getId() == id)
                return category;
        }
        return null;
    }

    // convert image from bitmap to byte array
    private static byte[] getImageBytes(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert image from byte array to bitmap
    private static Bitmap getImageBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
