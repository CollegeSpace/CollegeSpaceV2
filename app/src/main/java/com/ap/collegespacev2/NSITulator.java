package com.ap.collegespacev2;

import android.os.Bundle;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class NSITulator  extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsitulator, 4);

        getSupportActionBar().setTitle(getString(R.string.nsitulatorTitle));
    }
}
