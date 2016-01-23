package com.ap.collegespacev2;

import android.os.Bundle;

public class Home extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home, 0);

        getSupportActionBar().setTitle("Home");
    }
}
