package com.sebasdev.gravilitytest.util;

import android.util.Log;

import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;
import com.sebasdev.gravilitytest.network.ServiceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by m4605 on 19/03/16.
 */
public class DataManager {

    private static final String SERVICE_URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    private static ArrayList<App> apps = null;
    private static ArrayList<Category> categories = null;

    public static ArrayList<App> getApps() {

        return apps;
    }

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

    public static ArrayList<Category> getCategories() {

        return categories;
    }

    public static void getServiceData() {
        // TODO: 19/03/16 procesar json
        // TODO: 19/03/16 validar si hay conexion para obtenerlos de internet o de la bd

        ServiceRequest request = new ServiceRequest();
        try {
            String result = request.requestGet(SERVICE_URL);

            JSONObject jsonResult = new JSONObject(result);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DataManager", "Error al obtener respuesta del servidor");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DataManager", "Error al procesar la respuesta, parece que no es un JSON valido");
        }

        categories = new ArrayList<>();

        categories.add(new Category(1, "Deportes"));
        categories.add(new Category(2, "Entretenimiento"));
        categories.add(new Category(3, "Juegos"));
        categories.add(new Category(4, "Adultos"));

        apps = new ArrayList<>();

        apps.add(new App("App uno", "Autor de app", "USD 1.9", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur lacus nulla, rutrum sodales condimentum a, ullamcorper pretium lorem.", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
        apps.add(new App("App dos", "Autor de app", "USD 0.5", "App de prueba 2", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
        apps.add(new App("App tres", "Autor de app", "USD 0.0", "App de prueba 3", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
        apps.add(new App("App cuatro", "Autor de app", "USD 0.0", "App de prueba 4", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(1)));
        apps.add(new App("App cinco", "Autor de app", "USD 1.9", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur lacus nulla, rutrum sodales condimentum a, ullamcorper pretium lorem.", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(1)));
    }
}
