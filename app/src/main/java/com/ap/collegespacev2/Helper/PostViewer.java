package com.ap.collegespacev2.Helper;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by amaneureka on 18-03-2016.
 */
public class PostViewer extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }
}
