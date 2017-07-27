package com.ap.collegespacev2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ap.collegespacev2.Adapter.NsitPediaAdapter;
import com.ap.collegespacev2.Helper.NsitPediaApi;
import com.ap.collegespacev2.Models.NpediaPosts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NsitPediaActivity extends BaseActivity {

    public static final String TAG = "NsitPediaActivity : " ;
    RecyclerView rvPosts ;
    NsitPediaAdapter adapter ;
    ArrayList<NpediaPosts> listPostsNPedias ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsit_pedia , 2);
        getSupportActionBar().setTitle("NSITpedia");
        rvPosts = (RecyclerView)findViewById(R.id.rvPostsNsitPedia) ;
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        listPostsNPedias = new ArrayList<>() ;
        adapter = new NsitPediaAdapter(listPostsNPedias, this, new NsitPediaAdapter.onItemCLickedListener() {
            @Override
            public void onItemCLicked(View view , NpediaPosts thisPost) {
                Log.d(TAG, "onItemCLicked: ");
                Intent i = new Intent(NsitPediaActivity.this , NSITpediaPostDetails.class) ;
                i.putExtra("title" , thisPost.getTitle()) ;
                i.putExtra("content" , thisPost.getContent()) ;
                i.putExtra("featured_image" , thisPost.getFeatured_image().getGuid()) ;
                i.putExtra("author" , thisPost.getAuthor().getUsername()) ;
                i.putExtra("link" , thisPost.getLink()) ;
                startActivity(i);
            }
        }) ;
        rvPosts.setAdapter(adapter);
        final ProgressDialog pd = new ProgressDialog(this) ;
        pd.setMessage("Loading Please wait...");
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nsitpedia.collegespace.in/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build() ;
        NsitPediaApi api = retrofit.create(NsitPediaApi.class) ;

        api.getPosts().enqueue(new Callback<ArrayList<NpediaPosts>>() {
            @Override
            public void onResponse(Call<ArrayList<NpediaPosts>> call, Response<ArrayList<NpediaPosts>> response) {
                if (pd.isShowing()){
                    pd.dismiss();
                }
                adapter.updatePosts(response.body());


            }

            @Override
            public void onFailure(Call<ArrayList<NpediaPosts>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
