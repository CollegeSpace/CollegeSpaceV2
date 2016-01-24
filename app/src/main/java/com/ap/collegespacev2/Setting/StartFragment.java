package com.ap.collegespacev2.Setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.ap.collegespacev2.R;

/**
 * Created by amaneureka on 24-Jan-16.
 */
public class StartFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_start);
    }
}
