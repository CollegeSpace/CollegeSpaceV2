package com.ap.collegespacev2.Helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.ap.collegespacev2.R;

/**
 * Created by amaneureka on 24-Jan-16.
 */
public class htmlDialog extends DialogPreference
{
    WebView content;
    String assetPath;

    public htmlDialog(Context oContext, AttributeSet attrs)
    {
        super(oContext, attrs);
        TypedArray a = oContext.obtainStyledAttributes(attrs, R.styleable.htmlDialog);
        assetPath = a.getString(R.styleable.htmlDialog_contentFile);

        setDialogLayoutResource(R.layout.dialog);
    }

    @Override
    protected void onBindDialogView(View view)
    {
        content = (WebView) view.findViewById(R.id.dialogView);
        content.loadUrl(assetPath);
        super.onBindDialogView(view);
    }
}
