package com.sebasdev.gravilitytest.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by m4605 on 19/03/16.
 */
public class ServiceRequest {

    private static final String DEBUG_TAG = "ServiceRequest";

    public ServiceRequest() {
    }

    public String requestGet(String url) throws IOException {
        return readString(requestGetInputStream(url));
    }

    public Bitmap requestImage(String url) throws IOException {
        return readImage(requestGetInputStream(url));
    }

    private InputStream requestGetInputStream(String url) throws IOException {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved web page content.
//        int len = 500;
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
//            String contentAsString = readIt(is /*, len*/);
//            Log.d(DEBUG_TAG, "The content is: " + contentAsString);
//
//            return contentAsString;
            return is;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readString(InputStream stream /*, int len*/) throws IOException {

        Reader reader = new InputStreamReader(stream, "UTF-8");

        BufferedReader br = new BufferedReader(reader);

        String temp = "";
        String result = "";

        while ((temp=br.readLine()) != null) {
            result += temp;
        }
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
        Log.d(DEBUG_TAG, "The content is: " + result);
        return result;
    }

    private Bitmap readImage(InputStream stream) {

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return  bitmap;
    }
}
