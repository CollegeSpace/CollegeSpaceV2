package com.ap.collegespacev2.Helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class jsonParser {

    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";

    public jsonParser(){ }

    public JSONArray getJSONFromUrl(String url)
    {
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        }
        catch (UnsupportedEncodingException e) { e.printStackTrace(); }
        catch (ClientProtocolException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + "n");
            is.close();
            json = sb.toString();
        }
        catch (Exception e) { Log.e("Buffer Error", "Error converting result " + e.toString()); }

        try { jObj = new JSONArray(json); }
        catch (JSONException e) { Log.e("JSON Parser", "Error parsing data " + e.toString()); }
        return jObj;
    }
}

