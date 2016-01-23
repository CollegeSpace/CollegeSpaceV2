package com.ap.collegespacev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class Launcher extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        LaunchApp();
    }

    private void LaunchApp()
    {
        Intent start = new Intent(Launcher.this, Home.class);
        startActivity(start);
        this.finish();
    }
}
