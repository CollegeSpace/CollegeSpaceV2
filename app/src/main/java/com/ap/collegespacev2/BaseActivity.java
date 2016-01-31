package com.ap.collegespacev2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ap.collegespacev2.Adapter.DrawerListAdapter;
import com.ap.collegespacev2.Helper.NavItem;

import java.util.ArrayList;

/**
 * Created by amaneureka on 23-Jan-16.
 */
public class BaseActivity extends ActionBarActivity
{
    ArrayList<NavItem> mNavItems;
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
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.empty, R.string.empty)
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
        mNavItems =  new ArrayList<NavItem>();
        mNavItems.add(new NavItem("Updates", R.drawable.ic_action_links));
        mNavItems.add(new NavItem("NSITpedia", R.drawable.ic_author));
        mNavItems.add(new NavItem("Bookmarks", R.drawable.ic_action_like));
        mNavItems.add(new NavItem("TimeTable", R.drawable.ic_action_new_comment));
        mNavItems.add(new NavItem("NSITulator", R.drawable.ic_action_like));
        mNavItems.add(new NavItem("Settings", R.drawable.ic_action_settings));

        drawerList.setAdapter(new DrawerListAdapter(this, mNavItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerList.setItemChecked(drawerId, true);
    }

    public void setItemChecked() {
        drawerList.setItemChecked(drawerId, true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        drawerList.setItemChecked(drawerId, true);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void displayDrawerToggle(boolean ddt)
    {
        drawerToggle.setDrawerIndicatorEnabled(ddt);
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
            if (drawerId == position)
            {
                if (!isTablet)
                    drawerLayout.closeDrawer(drawerList);
                return;
            }

            switch (position)
            {
                case 0://Updates
                    Intent updates = new Intent(BaseActivity.this, Home.class);
                    startActivity(updates);
                    if (!isTablet)
                        drawerLayout.closeDrawer(drawerList);
                    break;
                case 4://NSITulator
                    Intent nsitulator = new Intent(BaseActivity.this, NSITulator.class);
                    startActivity(nsitulator);
                    if (!isTablet)
                        drawerLayout.closeDrawer(drawerList);
                    break;
                case 5://Settings
                    Intent settings = new Intent(BaseActivity.this, Settings.class);
                    startActivity(settings);
                    if (!isTablet)
                        drawerLayout.closeDrawer(drawerList);
                    break;
                default:
                    Log.d("Drawer", "Unexpected Menu Item Selection.");
                    break;
            }
        }
    };
}
