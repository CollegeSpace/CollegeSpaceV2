package com.ap.collegespacev2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ap.collegespacev2.Adapter.PostsListAdapter;
import com.ap.collegespacev2.Helper.DBHelper;
import com.ap.collegespacev2.Helper.UpdatesItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by amaneureka on 19-03-2016.
 */
public class Bookmarks  extends BaseActivity
{
    DBHelper mDBHelper;
    ListView mBookmarksList;
    PostsListAdapter mBookmarksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks, 2);

        getSupportActionBar().setTitle(getString(R.string.bookmarksTitle));

        mDBHelper = new DBHelper(this);
        mBookmarksList = (ListView)findViewById(R.id.posts_list_bookmarks);
        UpdateList();
    }

    private void UpdateList()
    {
        mBookmarksAdapter = new PostsListAdapter(this, getBookmarkedItems());
        mBookmarksList.setVisibility(View.VISIBLE);
        mBookmarksList.setAdapter(mBookmarksAdapter);

        mBookmarksList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
                UpdatesItem post = mBookmarksAdapter.getItem(position);
                Intent intent = new Intent(Bookmarks.this, PostDetails.class);
                intent.putExtra("post_title_bar", "Updates");
                intent.putExtra("post_id", Integer.toString(post.getID()));
                intent.putExtra("post_title", post.getTitle());
                intent.putExtra("post_content", post.getContent());
                intent.putExtra("post_url", post.getLink());
                intent.putExtra("post_date", post.getDate());
                startActivity(intent);
            }
        });
    }

    private ArrayList<UpdatesItem> getBookmarkedItems()
    {
        ArrayList<UpdatesItem> resArray = new ArrayList<UpdatesItem>();

        SharedPreferences pref = getSharedPreferences("UPDATES_FAV", 0);
        Map<String, ?> items = pref.getAll();
        for (Map.Entry<String, ?> entry : items.entrySet())
        {
            int val = pref.getInt(entry.getKey(), 0);

            if (val != 0) {
                Log.d("@Bookmarks", "Post ID: " + val);
                resArray.add(mDBHelper.getUpdateItemFromID(val));
            }
        }
        return  resArray;
    }
}
