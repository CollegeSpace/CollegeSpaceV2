package com.ap.collegespacev2.Helper;

import com.ap.collegespacev2.Models.NpediaPosts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by anirudh on 26/07/17.
 */

public interface NsitPediaApi {

    @GET("posts")
    Call<ArrayList<NpediaPosts>> getPosts() ;
}
