package com.sebasdev.gravilitytest.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.sebasdev.gravilitytest.db.DataBaseConnexion;
import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;
import com.sebasdev.gravilitytest.network.ServiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by m4605 on 19/03/16.
 * Manage the app data
 */
public class DataManager {

    private static final String SERVICE_URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    private static ArrayList<App> apps = new ArrayList<>();
    private static ArrayList<Category> categories = new ArrayList<>();

    /**
     * Return apps
     */
    public static ArrayList<App> getApps() {
        return apps;
    }

    /**
     * Return categories
     */
    public static ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     * Return apps that match the category parameter
     */
    public static ArrayList<App> getAppsByCategory(Category category) {

        ArrayList<App> appsByCategory = new ArrayList<>();

        if (apps != null) {

            for (App app : apps) {

                if (app.getCategory().equals(category)){
                    appsByCategory.add(app);
                }
            }

            return appsByCategory;

        } else {
            return null;
        }
    }

    /**
     * Return a category that match the id parameter
     */
    public static Category getCategory(int id) {

        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    /**
     * Return true if exists a category that match the id parameter
     */
    public static boolean isCategory(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the app data from the server
     */
    public static void getServiceData(Context context) throws IOException, JSONException {

        ServiceRequest request = new ServiceRequest();

        String result = request.requestString(SERVICE_URL);

        JSONObject jsonResult = new JSONObject(result);

        JSONObject feed = jsonResult.getJSONObject("feed");
        JSONArray entry = feed.getJSONArray("entry");

        // process JSON
        for (int i=0; i<entry.length(); i++) {

            App app = new App();
            JSONObject appJson = entry.getJSONObject(i);

            JSONObject appName = appJson.getJSONObject("im:name");
            app.setName(appName.getString("label"));

            JSONArray imgArray = appJson.getJSONArray("im:image");
            app.setImage(getUrlImage(imgArray));

            JSONObject appSummary = appJson.getJSONObject("summary");
            app.setDescription(appSummary.getString("label"));

            JSONObject appPriceAttributes = appJson.getJSONObject("im:price").getJSONObject("attributes");
            app.setPrice(appPriceAttributes.getString("currency") + " " + appPriceAttributes.getString("amount"));

            JSONObject appAuthor = appJson.getJSONObject("im:artist");
            app.setAuthor(appAuthor.getString("label"));

            JSONObject catAttributes = appJson.getJSONObject("category").getJSONObject("attributes");
            int idCategory = Integer.parseInt(catAttributes.getString("im:id"));

            Bitmap imageBitmap = request.requestImage(app.getImage());
            app.setImageBitmap(imageBitmap);

            // set the category if it exists, else create a new category
            if (isCategory(idCategory)) {
                app.setCategory(getCategory(idCategory));

            } else {
                Category category = new Category();

                category.setId(idCategory);
                category.setLabel(catAttributes.getString("label"));

                app.setCategory(category);

                categories.add(category);
            }

            apps.add(app);
        }

        // save apps and categories in database
        DataBaseConnexion conn = new DataBaseConnexion(context);
        conn.syncDataBase(apps, categories);
    }

    /**
     * Get the app data from the SQLite database
     */
    public static void getCachedData(Context context) {

        DataBaseConnexion conn = new DataBaseConnexion(context);

        apps = conn.getApps();
        categories = conn.getCategories();
    }

    /**
     * Get the URL of image from a JSONArray that contains various image's url
     */
    private static String getUrlImage(JSONArray imgArray) throws JSONException {

        // search the biggest image
        for (int i=0; i<imgArray.length(); i++) {

            JSONObject imgObj = imgArray.getJSONObject(i);
            JSONObject imgAttributes = imgObj.getJSONObject("attributes");

            if (imgAttributes.getString("height").equals("100")) {
                return imgObj.getString("label");
            }
        }
        // if not find the biggest image, return the first one
        return imgArray.getJSONObject(0).getString("label");
    }
}
