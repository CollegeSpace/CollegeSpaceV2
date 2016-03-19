package com.ap.collegespacev2.Helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    static String json = "";

    public jsonParser(){ }

    private void getJSONFromURL(String url)
    {
        Log.d("@getJSON", url);
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

            //Sometimes it shows some warnings, so parse it out
            int index1 = json.indexOf('[');
            int index2 = json.indexOf('{');
            if (index1 != -1)
                index2 = Math.min(index1, index2);
            json = json.substring(index2);
        }
        catch (Exception e) { Log.e("Buffer Error", "Error converting result " + e.toString()); }
    }

    public JSONArray getJSONArrayFromUrl(String url)
    {
        try { getJSONFromURL(url); return new JSONArray(json); }
        catch (JSONException e) { Log.e("@JSONArrayParser", "Error parsing data " + e.toString()); }
        return null;
    }

    public JSONObject getJSONObjectFromUrl(String url)
    {
        try { getJSONFromURL(url); return new JSONObject(json); }
        catch (JSONException e) { Log.e("@JSONObjectParser", "Error parsing data " + e.toString()); }
        return null;
    }
}

