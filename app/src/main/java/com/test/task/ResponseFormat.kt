package com.test.task

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

class ResponseFormat {
    @SerializedName("results")
    private var jsonArray: JsonArray = JsonArray()

    @SerializedName("total_pages")
    private var pages: Int = 0

    fun getJsonArray(): JsonArray {
        return jsonArray
    }

    fun getPages(): Int {
        return pages
    }
}