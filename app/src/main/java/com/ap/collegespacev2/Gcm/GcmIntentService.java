package com.ap.collegespacev2.Gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class GcmIntentService  extends IntentService
{
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
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
                    Log.e("update", extras.getString("update")); //sendUpdateNotification(extras.getString("update"));
                else if(extras.getString("new_post") != null)
                    Log.e("new_post", extras.getString("new_post")); //sendNewNotification(extras.getString("new_post"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
