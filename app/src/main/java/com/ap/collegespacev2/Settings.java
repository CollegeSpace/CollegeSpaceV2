package com.ap.collegespacev2;

import android.os.Bundle;

import com.ap.collegespacev2.Setting.InfoFragment;
import com.ap.collegespacev2.Setting.NotificationFragment;
import com.ap.collegespacev2.Setting.StartFragment;

/**
 * Created by amaneureka on 24-Jan-16.
 */
public class Settings extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings, 5);

        getSupportActionBar().setTitle(getString(R.string.settingsTitle));

        String action = getIntent().getAction();
        displayDrawerToggle(false);

        if (action != null && action.equals("pref_dis_info"))
        {
            //About Button
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new InfoFragment())
                    .commit();
        }
        else if (action != null && action.equals("pref_dis_notification"))
        {
            //Notification Setting Button
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new NotificationFragment())
                    .commit();
        }
        else
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new StartFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        displayDrawerToggle(true);
    }
}
