package com.sebasdev.gravilitytest.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by m4605 on 19/03/16.
 */
public class ServiceRequest {

    private static final String DEBUG_TAG = "ServiceRequest";

    public ServiceRequest() {
    }

    public String requestString(String url) throws IOException {
        return requestGet(url);
    }

    public Bitmap requestImage(String url) throws IOException {
        return requestGetImage(url);
    }

    private String requestGet(String url) throws IOException {

        InputStream is = null;

        try {
            URL urlRequest = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlRequest.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readString(is /*, len*/);
            Log.d(DEBUG_TAG, "The content is: " + contentAsString);

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private Bitmap requestGetImage(String url) throws IOException {

        InputStream is = null;

        try {
            URL urlRequest = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlRequest.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a bitmap
            Bitmap contentAsBitmap = readImage(is);

            Log.d(DEBUG_TAG, "The image density is: " + contentAsBitmap.getDensity());

            return contentAsBitmap;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readString(InputStream stream) throws IOException {

        Reader reader = new InputStreamReader(stream, "UTF-8");

        BufferedReader br = new BufferedReader(reader);

        String temp, result = "";

        temp = br.readLine();

        while (temp != null) {
            result += temp;
            temp = br.readLine();
        }
        return result;
    }

    private Bitmap readImage(InputStream stream) {

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return  bitmap;
    }
}
