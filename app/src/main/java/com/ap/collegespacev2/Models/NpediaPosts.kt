package com.ap.collegespacev2.Models

/**
 * Created by anirudh on 26/07/17.
 */
data class NpediaPosts (var ID : Int , var title : String , var content : String ,
                        var link : String , var author : Author , var excerpt : String) {

    data class Author(var username : String  , var avatar : String)
}