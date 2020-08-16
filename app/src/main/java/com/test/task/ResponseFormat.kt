package com.test.task

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

class ResponseFormat {
    @SerializedName("results")
    private var arrayList : ArrayList<Movie> = ArrayList();
    //private var jsonArray: JsonArray = JsonArray()


    fun getJsonArray(): ArrayList<Movie> {
        return arrayList
    }

    class Movie {
        @SerializedName("poster_path")
        var poster_path = ""

        @SerializedName("title")
        var title = ""

        @SerializedName("vote_average")
        var vote_average = 0f

        @SerializedName("overview")
        var overview = ""

        @SerializedName("release_date")
        var release_date = ""
    }
}