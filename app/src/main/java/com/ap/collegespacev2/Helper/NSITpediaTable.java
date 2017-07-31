package com.ap.collegespacev2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ap.collegespacev2.Models.DBNPediaPosts;
import com.ap.collegespacev2.Models.NpediaPosts;

import java.util.ArrayList;

import static com.ap.collegespacev2.Helper.DBUtils.*;

/**
 * Created by anirudh on 29/07/17.
 */

public class NSITpediaTable {
    public static final String TAG = "NSITpediaTable";
    public static final String TABLE_NAME = "NSITpedia";

    interface Columns {
        String COL_ID = "id";
        String COL_EXCERPT = "excerpt";
        String COL_TITLE = "title";
        String COL_AUTHOR_NAME = "author";
        String COL_AUTHOR_AVATAR = "avatar";
        String COL_IMAGE = "image";
        String COL_CONTENT = "content" ;

    }

    public static final String CMD_CREATE_TABLE =
            CREATE_TABLE +
                    TABLE_NAME + LBR +
                    Columns.COL_ID + TYPE_INT_PK + COMMA +
                    Columns.COL_TITLE + TYPE_TEXT + COMMA +
                    Columns.COL_EXCERPT + TYPE_TEXT + COMMA +
                    Columns.COL_CONTENT + TYPE_TEXT + COMMA +
                    Columns.COL_AUTHOR_NAME + TYPE_TEXT +
                    RBR + SEMI;


    public static ArrayList<NpediaPosts> getNPediaPosts(SQLiteDatabase db) {

        ArrayList<DBNPediaPosts> posts = new ArrayList<>();
        ArrayList<NpediaPosts> npediaPostses = new ArrayList<>() ;
        Cursor csr = db.query(TABLE_NAME
                , new String[]{
                        Columns.COL_TITLE,
                        Columns.COL_ID,
                        Columns.COL_EXCERPT,
                        Columns.COL_AUTHOR_NAME ,
                        Columns.COL_CONTENT
                },
                null,
                null,
                null,
                null,
                Columns.COL_ID + " DESC"
        );
        while (csr.moveToNext()) {
            posts.add(
                    new DBNPediaPosts(
                            csr.getInt(csr.getColumnIndex(Columns.COL_ID)),
                            csr.getString(csr.getColumnIndex(Columns.COL_TITLE)),
                            csr.getString(csr.getColumnIndex(Columns.COL_EXCERPT)),
                            csr.getString(csr.getColumnIndex(Columns.COL_AUTHOR_NAME)))
            );
            npediaPostses.add(
                    new NpediaPosts(
                            csr.getInt(csr.getColumnIndex(Columns.COL_ID)),
                            csr.getString(csr.getColumnIndex(Columns.COL_TITLE)),
                            csr.getString(csr.getColumnIndex(Columns.COL_CONTENT)) ,
                            "link" ,
                            new NpediaPosts.Author(csr.getString(csr.getColumnIndex(Columns.COL_AUTHOR_NAME)) , "avatar") ,
                            csr.getString(csr.getColumnIndex(Columns.COL_EXCERPT)),
                            new NpediaPosts.FeaturedImage("guid")
                    )
            ) ;

        }

        Log.d(TAG, "getNPediaPosts: " + npediaPostses.size());
        return npediaPostses;
    }


    public static void addData(SQLiteDatabase db, int id, String title, String excerpt, String author_name , String content) {

            Cursor c = db.query(TABLE_NAME ,
                    new String[]{Columns.COL_ID} , Columns.COL_ID + "= ?"
                    ,new String[]{String.valueOf(id)}
                    , null ,null  ,null , null)  ;

        if(c.moveToNext()){
            Log.d(TAG, "addData: Not added already exist  : " +  id);
            return;
        }
            ContentValues cv = new ContentValues();
            cv.put(Columns.COL_ID, id);
            cv.put(Columns.COL_TITLE, title);
            cv.put(Columns.COL_EXCERPT, excerpt);
            cv.put(Columns.COL_AUTHOR_NAME, author_name);
            cv.put(Columns.COL_CONTENT , content);
            db.insert(TABLE_NAME, null, cv);

    }

    public static void deleteTable(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TABLE);
    }
}
