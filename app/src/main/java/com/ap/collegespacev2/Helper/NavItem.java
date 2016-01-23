package com.ap.collegespacev2.Helper;

/**
 * Created by amaneureka on 24-Jan-16.
 */
public class NavItem
{
    String mTitle;
    int mIcon;

    public String Title() { return mTitle; }

    public int Icon() { return  mIcon; }

    public NavItem(String aTitle, int aIcon)
    {
        mTitle = aTitle;
        mIcon = aIcon;
    }
}
