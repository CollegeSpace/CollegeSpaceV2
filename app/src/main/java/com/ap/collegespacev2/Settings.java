package com.ap.collegespacev2;

import android.os.Bundle;

import com.ap.collegespacev2.Setting.InfoFragment;
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
        setContentView(R.layout.activity_settings, 4);

        getSupportActionBar().setTitle(getString(R.string.settingsTitle));

        String action = getIntent().getAction();

        if (action != null && action.equals("pref_dis_info"))
        {
            displayDrawerToggle(false);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new InfoFragment())
                    .commit();
        }
        else
        {
            displayDrawerToggle(true);
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
