package com.tetiana.bakingapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


class DataLoader {
    static JSONArray getJSONfromURL() {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://baking.s3-website-eu-west-1.amazonaws.com");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }


        StringBuffer sb = new StringBuffer("");
        try {
            URL urls = new URL("http://baking.s3-website-eu-west-1.amazonaws.com");
            URLConnection urlConnection;
            urlConnection = urls.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
            result=sb.toString();
        } catch (IOException ignored) {}
        catch (Exception ignored) {}
        try {

            jArray = new JSONArray(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }
}
