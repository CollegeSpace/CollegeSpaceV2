package com.ap.collegespacev2.Gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ap.collegespacev2.BaseActivity;
import com.ap.collegespacev2.Helper.DBHelper;
import com.ap.collegespacev2.Helper.Misc;
import com.ap.collegespacev2.Helper.UpdatesItem;
import com.ap.collegespacev2.Helper.jsonParser;
import com.ap.collegespacev2.PostDetails;
import com.ap.collegespacev2.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class GcmIntentService  extends IntentService
{
    DBHelper mDBHelper;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    String mAPIBaseUrl = "http://updates.collegespace.in/wp-json/posts/";

    int mNotificationDefaults;
    public GcmIntentService()
    {
        super("GcmIntentService");
        mDBHelper = new DBHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty())
        {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                if(extras.getString("message") != null)
                    Log.e("message", extras.getString("message"));//sendMessageNotification(extras.getString("message"));
                else if(extras.getString("update") != null)
                    sendMessageNotification(extras.getString("update"), "notify_update_post");
                else if(extras.getString("new_post") != null)
                    sendMessageNotification(extras.getString("new_post"), "notify_new_post");
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private boolean sendMessageNotification(String Response, String PrefKey) {

        Log.d("@sendNotification", Response);

        try
        {
            String PostDataUrl = mAPIBaseUrl + Response;
            new PostSyncAndSendNotification().execute(PostDataUrl, PrefKey);

            return true;
        }
        catch (Exception e)
        { Log.e("@sendNotification", e.toString()); }
        return false;
    }

    protected class PostSyncAndSendNotification extends AsyncTask<String, Integer, UpdatesItem>
    {
        @Override
        protected void onPostExecute(UpdatesItem post)
        {
            if (post != null)
            {
                mBuilder = new NotificationCompat.Builder(getBaseContext());
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mBuilder.setSmallIcon(R.mipmap.ic_cs);
                mBuilder.setContentTitle("CollegeSpace Updates");
                mBuilder.setContentText(post.getTitle());
                mBuilder.setDefaults(mNotificationDefaults);

                Intent postDetailsIntent = new Intent(GcmIntentService.this, PostDetails.class);
                postDetailsIntent.putExtra("post_title_bar", "Updates");
                postDetailsIntent.putExtra("post_id", Integer.toString(post.getID()));
                postDetailsIntent.putExtra("post_title", post.getTitle());
                postDetailsIntent.putExtra("post_content", post.getContent());
                postDetailsIntent.putExtra("post_url", post.getLink());
                postDetailsIntent.putExtra("post_date", post.getDate());

                mBuilder.setContentIntent(PendingIntent.getActivity(GcmIntentService.this, 0, postDetailsIntent, PendingIntent.FLAG_ONE_SHOT));

                mNotificationManager.notify(post.getID(), mBuilder.build());
            }
        }

        @Override
        protected UpdatesItem doInBackground(String... params)
        {
            String download_url = params[0];
            try
            {
                //Some pref related stuffs -- less managed
                //TODO: Manage this code
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean notifyme = prefs.getBoolean("notify_me", true);
                boolean donotify = prefs.getBoolean(params[1], true);

                mNotificationDefaults = Notification.DEFAULT_LIGHTS;
                if (prefs.getBoolean("notify_broadcast_vibrate", true))
                    mNotificationDefaults |= Notification.DEFAULT_VIBRATE;
                if (prefs.getBoolean("notify_broadcast_sound", true))
                    mNotificationDefaults |= Notification.DEFAULT_SOUND;

                jsonParser jParser = new jsonParser();
                JSONObject feedObj = jParser.getJSONObjectFromUrl(download_url);
                UpdatesItem aItem = Misc.getUpdateItemObject(getResources(), feedObj);

                mDBHelper.UpdatesDeleteItemIfExist(aItem.getID());
                mDBHelper.UpdatesAddItemIfNotExist(aItem);

                if (notifyme && donotify)
                    return aItem;
            }
            catch (Exception ex)
            { Log.e("@GcmIntentService", ex.getMessage()); }
            return null;
        }
    }
}
