package com.ap.collegespacev2.Setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.ap.collegespacev2.R;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class NotificationFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_notification);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }
}
