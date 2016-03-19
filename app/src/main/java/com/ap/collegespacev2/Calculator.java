package com.ap.collegespacev2;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.ap.collegespacev2.Helper.Misc;
import com.ap.collegespacev2.Helper.PostViewer;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class Calculator  extends BaseActivity
{
    String mBranch;
    String mSemester;
    String mContent;

    WebView mCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks, 4);

        getSupportActionBar().setTitle(getString(R.string.nsitulatorTitle));

        mBranch = getIntent().getStringExtra("nsitulator_branch");
        mSemester = getIntent().getStringExtra("nsitulator_semester");

        try
        {
            StringBuilder SB = new StringBuilder();

            JSONObject mObject = new JSONObject(Misc.getStringFromInputStream(getAssets().open("nsitulator_data.json")));
            mObject = mObject.getJSONArray(mBranch).getJSONObject(0).getJSONObject(mSemester);

            Iterator<String> mSubjects =  mObject.keys();
            while(mSubjects.hasNext())
            {
                String subj = (String)mSubjects.next();
                SB.append("<tr><td>");
                SB.append(subj);
                SB.append("</td><td>");
                SB.append(mObject.getInt(subj));
                SB.append("</td></tr>");
            }
            mContent = Misc.getStringFromInputStream(getAssets().open("calculator.html"));
            mContent = mContent.replace("%code%", SB.toString());
        }
        catch (Exception e) { Log.e("@Calculator", e.toString()); }

        //load Canvas settings
        mCanvas = (WebView)findViewById(R.id.marks_canvas);
        mCanvas.setWebViewClient(new PostViewer());
        mCanvas.setBackgroundColor(0xffeeeeee);//Android's default background color
        mCanvas.loadData(mContent, "text/html", "utf-8");
    }
}

