package com.ap.collegespacev2.Helper;

import android.content.res.Resources;
import android.util.Log;

import com.ap.collegespacev2.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by amaneureka on 19-03-2016.
 */
public class Misc
{
    public static String getStringFromInputStream(InputStream is)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try
        {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null)
                sb.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
            {
                try { br.close(); }
                catch (IOException e)
                { e.printStackTrace(); }
            }
        }
        return sb.toString();
    }

    public static UpdatesItem getUpdateItemObject(Resources mResources, JSONObject feedObj)
    {
        try {
            UpdatesItem afeedObj = new UpdatesItem(
                    feedObj.getInt(mResources.getString(R.string.wp_plugin_id)),
                    feedObj.getString(mResources.getString(R.string.wp_plugin_title)),
                    feedObj.getString(mResources.getString(R.string.wp_plugin_content)),
                    feedObj.getString(mResources.getString(R.string.wp_plugin_link)),
                    feedObj.getString(mResources.getString(R.string.wp_plugin_date)),
                    feedObj.getString(mResources.getString(R.string.wp_plugin_modified))
            );
            return afeedObj;
        }
        catch (Exception e)
        {
            Log.e("@Misc", e.toString());
        }

        return null;
    }
}
