package com.ap.collegespacev2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class PostDetails extends ActionBarActivity
{
    String aID;
    boolean isPostFav = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);

        aID = getIntent().getStringExtra("post_id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);
        SharedPreferences pref = getSharedPreferences("PREFERENCE_APP", 0);
        String favs = pref.getString("Favs" , "-1");
        if (favs.contains(aID))
        {
            isPostFav = true;
            MenuItem nofav = menu.findItem(R.id.menu_not_fav);
            nofav.setVisible(false);
        }
        else
        {
            isPostFav = false;
            MenuItem fav = menu.findItem(R.id.menu_fav);
            fav.setVisible(false);
        }
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_share:
            case R.id.menu_web:
                return true;
            case R.id.menu_fav:
                isPostFav = false;
                removeFav();
                supportInvalidateOptionsMenu();
                return true;
            case R.id.menu_not_fav:
                isPostFav = true;
                addFav();
                supportInvalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFav()
    {
        SharedPreferences pref = getSharedPreferences("PREFERENCE_APP", 0);
        String favs = pref.getString("Favs" , null);
        if (favs != null)
        {
            String newfav = favs+aID+",";
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Favs", newfav);
            editor.commit();
        }
        else
        {
            String newfav = aID+",";
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Favs", newfav);
            editor.commit();
        }
        Toast.makeText(this, getString(R.string.msg_fav_added) ,Toast.LENGTH_SHORT).show();
    }

    private void removeFav()
    {
        SharedPreferences pref = getSharedPreferences("PREFERENCE_APP", 0);
        String favs = pref.getString("Favs" , null);
        String actfav =aID+ ",";
        String editfav = favs.replace(actfav, "");
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Favs", editfav);
        editor.commit();
        Toast.makeText(this, getString(R.string.msg_fav_removed) ,Toast.LENGTH_SHORT).show();
    }
}
