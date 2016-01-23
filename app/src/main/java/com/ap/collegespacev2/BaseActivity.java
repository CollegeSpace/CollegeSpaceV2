package com.ap.collegespacev2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by amaneureka on 23-Jan-16.
 */
public class BaseActivity extends ActionBarActivity
{
    ListView drawerList;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    View currentView;
    int drawerId;
    boolean isTablet = false;

    protected FrameLayout contentLayout;

    public void setContentView(final int aLayoutResID, int aDrawerId)
    {
        this.drawerId = aDrawerId;

        currentView = (View) getLayoutInflater().inflate(R.layout.activity_base, null);

        Toolbar toolbar = (Toolbar) currentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) currentView.findViewById(R.id.drawer_layout);
        contentLayout = (FrameLayout) currentView.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(aLayoutResID, contentLayout, true);
        super.setContentView(currentView);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if(((ViewGroup.MarginLayoutParams)contentLayout.getLayoutParams()).leftMargin == 240) {
            isTablet = true;
            supportInvalidateOptionsMenu();
        }
        else
        {
            isTablet = false;
            supportInvalidateOptionsMenu();
        }

        if(isTablet)
        {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            drawerLayout.setScrimColor(getResources().getColor(R.color.transparent));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);

        }
        else if(!isTablet)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
            {
                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                }
            };

            drawerLayout.setDrawerListener(drawerToggle);
            drawerToggle.setDrawerIndicatorEnabled(true);
        }

        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] menu = getResources().getStringArray(R.array.menu);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.row_drawer_menu, menu));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerList.setItemChecked(drawerId, true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        if(!isTablet)
            drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if(!isTablet)
            drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (!isTablet && drawerToggle.onOptionsItemSelected(item))
            return true;
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            /*switch (position)
            {

            }*/
        }
    };
}
