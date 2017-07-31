package com.ap.collegespacev2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.collegespacev2.Helper.PostViewer;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class NSITpediaPostDetails extends ActionBarActivity {

    public static final String TAG = "NSITpediaPostDetails" ;
    TextView tvTitle ;
    WebView  webView ;
    ImageView ivFeaturedImage ;
    String link  , title ;
    Integer id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsitpedia_post_details);

        tvTitle = (TextView) findViewById(R.id.tvTitleNPediaDetails);
        webView = (WebView) findViewById(R.id.webViewNsitPedia) ;

        ivFeaturedImage = (ImageView)findViewById(R.id.ivFeaturedImageNPediaDetails) ;
        getSupportActionBar().setTitle("NSITpedia");

        title = getIntent().getStringExtra("title") ;
        String featured_image = getIntent().getStringExtra("featured_image") ;
        String content = getIntent().getStringExtra("content") ;
        String author = getIntent().getStringExtra("author") ;
        link  = getIntent().getStringExtra("link") ;
        id = getIntent().getIntExtra("id" , 0) ;

        tvTitle.setText(title);
        webView.setWebViewClient(new PostViewer());
        webView.setBackgroundColor(0xffeeeeee);//Android's default background color
        webView.loadData(content, "text/html", "utf-8");

        Glide.with(this).load(featured_image).into(ivFeaturedImage) ;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mi = getMenuInflater() ;
        mi.inflate(R.menu.details , menu);
        MenuItem fav = menu.findItem(R.id.menu_fav);
        fav.setVisible(false);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menu_share :
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(title) + "\n" + link);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getString(R.string.action_share)));
                break ;
            case R.id.menu_web :  startActivity(new Intent(Intent.ACTION_VIEW  , Uri.parse(link)));
                break ;
            case R.id.menu_fav :
                break;
            case R.id.menu_not_fav :
                break ;
            default:
                Log.d(TAG, "onOptionsItemSelected: Wrong option " );

        }

        return super.onOptionsItemSelected(item);
    }



}
