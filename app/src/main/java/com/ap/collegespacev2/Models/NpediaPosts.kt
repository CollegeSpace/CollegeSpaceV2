package com.ap.collegespacev2.Models

import android.support.annotation.Nullable

/**
 * Created by anirudh on 26/07/17.
 */
data class NpediaPosts (var ID : Int, var title : String, var content : String,
                         var link : String, var author : Author,
                        var excerpt : String, var featured_image : FeaturedImage) {
    data class Author(var username : String  ,  var avatar : String)
    data class FeaturedImage( var guid : String)
}