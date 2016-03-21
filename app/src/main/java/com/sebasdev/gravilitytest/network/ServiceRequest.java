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
 * Manage the request to the server
 */
public class ServiceRequest {

    private static final String DEBUG_TAG = "ServiceRequest";

    public ServiceRequest() {
    }

    /**
     * Request a String response using GET method
     */
    public String requestString(String url) throws IOException {
        return requestGet(url);
    }

    /**
     * Request an image in Bitmap using GET method
     */
    public Bitmap requestImage(String url) throws IOException {
        return requestGetImage(url);
    }

    /**
     * Do the request with String response
     */
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
//            Log.d(DEBUG_TAG, "The response is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readString(is);

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Do the request with Bitmap response
     */
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
//            Log.d(DEBUG_TAG, "The response loading image is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a bitmap
            Bitmap contentAsBitmap = readImage(is);

            return contentAsBitmap;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Convert a response in String
     */
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

    /**
     * Convert the response in Bitmap
     */
    private Bitmap readImage(InputStream stream) {

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return  bitmap;
    }
}
