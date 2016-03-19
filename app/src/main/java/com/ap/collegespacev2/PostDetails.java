package com.ap.collegespacev2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.ap.collegespacev2.Helper.Misc;
import com.ap.collegespacev2.Helper.PostViewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class PostDetails extends ActionBarActivity
{
    String mID;
    String mContent;
    String mURL;
    String mDate;
    String mTitle;
    String mActionBar;
    boolean isPostFav = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mActionBar = getIntent().getStringExtra("post_title_bar");
        mTitle = getIntent().getStringExtra("post_title");
        mID = getIntent().getStringExtra("post_id");
        mContent = getIntent().getStringExtra("post_content");
        mURL = getIntent().getStringExtra("post_url");
        mDate = getIntent().getStringExtra("post_date");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mActionBar);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Add CSS script
        try { mContent = mContent + Misc.getStringFromInputStream(getAssets().open("post_template.html")); }
        catch (Exception e) { Log.e("@PostDetails", e.toString()); }

        //Relative date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try
        {
            Date ddate = formatter.parse(mDate);
            long millis = ddate.getTime();
            mDate = DateUtils.getRelativeTimeSpanString(millis, System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS).toString();
        }
        catch(ParseException e) { }

        //Load Data
        ((TextView)findViewById(R.id.post_date)).setText(mDate);
        ((TextView)findViewById(R.id.post_title)).setText(mTitle);

        //Load Post Content
        WebView canvas = (WebView)findViewById(R.id.post_content);
        canvas.setWebViewClient(new PostViewer());
        canvas.setBackgroundColor(0xffeeeeee);//Android's default background color
        canvas.loadData(mContent, "text/html", "utf-8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);

        SharedPreferences pref = getSharedPreferences("UPDATES_FAV", 0);
        int favs = pref.getInt("id_" + mID , 0);
        if (favs != 0)
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
                shareContent();
                return true;
            case R.id.menu_web:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mURL));
                startActivity(i);
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
        SharedPreferences pref = getSharedPreferences("UPDATES_FAV", 0);
        int favs = pref.getInt("id_" + mID , 0);
        if (favs == 0)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("id_" + mID, Integer.parseInt(mID));
            editor.commit();
        }
        Toast.makeText(this, getString(R.string.msg_fav_added) ,Toast.LENGTH_SHORT).show();
    }

    private void removeFav()
    {
        SharedPreferences pref = getSharedPreferences("UPDATES_FAV", 0);
        int favs = pref.getInt("id_" + mID , 0);
        if (favs != 0)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("id_" + mID, 0);
            editor.commit();
        }
        Toast.makeText(this, getString(R.string.msg_fav_removed) ,Toast.LENGTH_SHORT).show();
    }

    private void shareContent()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(mTitle) + "\n" + mURL);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.action_share)));
    }
}
