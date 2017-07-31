package com.ap.collegespacev2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ap.collegespacev2.Adapter.NsitPediaAdapter;
import com.ap.collegespacev2.Helper.DBHelper;
import com.ap.collegespacev2.Helper.NSITpediaTable;
import com.ap.collegespacev2.Helper.NsitPediaApi;
import com.ap.collegespacev2.Models.NpediaPosts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NsitPediaActivity extends BaseActivity {

    public static final String TAG = "NsitPediaActivity : ";
    RecyclerView rvPosts;
    NsitPediaAdapter adapter;
    ArrayList<NpediaPosts> listPostsNPedias;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsit_pedia, 2);
        getSupportActionBar().setTitle("NSITpedia");
        rvPosts = (RecyclerView) findViewById(R.id.rvPostsNsitPedia);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        listPostsNPedias = new ArrayList<>();
        adapter = new NsitPediaAdapter(listPostsNPedias, this, new NsitPediaAdapter.OnItemCLickedListener() {
            @Override
            public void onItemCLicked(View view, NpediaPosts thisPost) {
                Log.d(TAG, "onItemCLicked: ");
                Intent i = new Intent(NsitPediaActivity.this, NSITpediaPostDetails.class);
                i.putExtra("title", thisPost.getTitle());
                i.putExtra("content", thisPost.getContent());
                i.putExtra("featured_image", thisPost.getFeatured_image().getGuid());
                i.putExtra("author", thisPost.getAuthor().getUsername());
                i.putExtra("link", thisPost.getLink());
                i.putExtra("id", thisPost.getID());
                startActivity(i);
            }
        });


        listPostsNPedias = NSITpediaTable.getNPediaPosts(db) ;
        adapter.updatePosts(listPostsNPedias);
        rvPosts.setAdapter(adapter);

        Log.d(TAG, "onCreate: deleted DataBase");
        Log.d(TAG, "onCreate: " + NSITpediaTable.getNPediaPosts(db).size());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nsitpedia.collegespace.in/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NsitPediaApi api = retrofit.create(NsitPediaApi.class);

        api.getPosts().enqueue(new Callback<ArrayList<NpediaPosts>>() {
            @Override
            public void onResponse(Call<ArrayList<NpediaPosts>> call, final Response<ArrayList<NpediaPosts>> response) {

                adapter.updatePosts(response.body());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (NpediaPosts post : response.body()) {
                            NSITpediaTable.addData(
                                    db,
                                    post.getID(),
                                    post.getTitle(),
                                    post.getExcerpt(),
                                    post.getAuthor().getUsername() ,
                                    post.getContent()
                            );
                        }
                    }
                }).run();



            }

            @Override
            public void onFailure(Call<ArrayList<NpediaPosts>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(NsitPediaActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
