package com.ap.collegespacev2.Helper;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class UpdatesItem
{
    Integer mID;
    String mTitle;
    String mContent;
    String mLink;
    String mData;
    String mModified;

    /* Properties */
    public Integer getID()      { return mID;      }
    public String getTitle()    { return mTitle;   }
    public String getContent()  { return mContent; }
    public String getLink()     { return mLink;    }
    public String getDate()     { return mData;    }
    public String getModified() { return mModified;}

    public UpdatesItem(Integer aID, String aTitle, String aContent, String aLink, String aDate, String aModified)
    {
        this.mID = aID;
        this.mTitle = aTitle;
        this.mContent = aContent;
        this.mLink = aLink;
        this.mData = aDate;
        this.mModified = aModified;
    }
}
