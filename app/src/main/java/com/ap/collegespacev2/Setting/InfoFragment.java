package com.ap.collegespacev2.Setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ap.collegespacev2.R;

/**
 * Created by amaneureka on 24-Jan-16.
 */
public class InfoFragment  extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_info);
    }
}
