package com.ap.collegespacev2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "collegespaceV2.db";
    public static final String TABLE_UPDATES_NAME = "updates";
    public static final String TABLE_UPDATES_KEY = "id";
    public static final String TABLE_UPDATES_TITLE = "title";
    public static final String TABLE_UPDATES_CONTENT = "content";
    public static final String TABLE_UPDATES_LINK = "link";
    public static final String TABLE_UPDATES_DATE = "date";
    public static final String TABLE_UPDATES_MODIFIED = "modified";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_UPDATES_NAME + " (" +
                TABLE_UPDATES_KEY + " INTEGER PRIMARY KEY, " +
                TABLE_UPDATES_TITLE + " TEXT, " +
                TABLE_UPDATES_CONTENT + " TEXT, " +
                TABLE_UPDATES_LINK + " TEXT, " +
                TABLE_UPDATES_DATE + " TEXT, " +
                TABLE_UPDATES_MODIFIED + " TEXT)");
        Log.i("DBHelper", "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("DBHelper", "onUpgrade() {" + Integer.toString(oldVersion) + "->" + Integer.toString(newVersion));
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPDATES_NAME);
        onCreate(db);
    }

    public Cursor getUpdatesFromID(Integer id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_UPDATES_NAME
                + " WHERE " + TABLE_UPDATES_KEY + "=" + Integer.toString(id), null);
    }

    public UpdatesItem getUpdateItemFromID(Integer id)
    {
        Cursor csr = getUpdatesFromID(id);
        csr.moveToFirst();
        UpdatesItem result = UpdateItemUtil(csr);
        csr.close();
        return  result;
    }

    public boolean UpdatesIDExist(Integer id)
    {
        Cursor result = getUpdatesFromID(id);
        boolean status = result.moveToFirst();
        result.close();
        return status;
    }

    public boolean UpdatesAddItemIfNotExist(UpdatesItem aItem)
    {
        if (!UpdatesIDExist(aItem.getID()))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(TABLE_UPDATES_KEY, aItem.getID());
            contentValues.put(TABLE_UPDATES_TITLE, aItem.getTitle());
            contentValues.put(TABLE_UPDATES_CONTENT, aItem.getContent());
            contentValues.put(TABLE_UPDATES_LINK, aItem.getLink());
            contentValues.put(TABLE_UPDATES_DATE, aItem.getDate());
            contentValues.put(TABLE_UPDATES_MODIFIED, aItem.getModified());
            db.insert(TABLE_UPDATES_NAME, null, contentValues);
            return true;
        }
        return false;
    }

    public ArrayList<UpdatesItem> UpdatesGetAllItemsIDLessThan(Integer aID)
    {
        return UpdatesGetAllItemsUtil("SELECT * FROM " + TABLE_UPDATES_NAME
                        + " WHERE " + TABLE_UPDATES_KEY + "<" + Integer.toString(aID) + " ORDER BY " + TABLE_UPDATES_KEY + " DESC LIMIT 10");
    }

    public ArrayList<UpdatesItem> UpdatesGetAllItemsID()
    {
        return UpdatesGetAllItemsUtil("SELECT * FROM " + TABLE_UPDATES_NAME + " ORDER BY " + TABLE_UPDATES_KEY + " DESC LIMIT 10");
    }

    private UpdatesItem UpdateItemUtil(Cursor query_res)
    {
        return new UpdatesItem(
                query_res.getInt(query_res.getColumnIndex(TABLE_UPDATES_KEY)),
                query_res.getString(query_res.getColumnIndex(TABLE_UPDATES_TITLE)),
                query_res.getString(query_res.getColumnIndex(TABLE_UPDATES_CONTENT)),
                query_res.getString(query_res.getColumnIndex(TABLE_UPDATES_LINK)),
                query_res.getString(query_res.getColumnIndex(TABLE_UPDATES_DATE)),
                query_res.getString(query_res.getColumnIndex(TABLE_UPDATES_MODIFIED))
        );
    }

    private ArrayList<UpdatesItem> UpdatesGetAllItemsUtil(String Query)
    {
        ArrayList<UpdatesItem> resArray = new ArrayList<UpdatesItem>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor query_res = db.rawQuery(Query, null);
        query_res.moveToFirst();

        UpdatesItem aItem;
        while(!query_res.isAfterLast())
        {
            aItem = UpdateItemUtil(query_res);
            resArray.add(aItem);
            query_res.moveToNext();
        }
        query_res.close();
        return resArray;
    }
}
