package com.test.task.retrofit

import com.test.task.ResponseFormat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface
{
    //3/movie/now_playing?api_key=1d9b898a212ea52e283351e521e17871&language=en&page=2&region=US
    @GET("3/movie/now_playing?api_key=1d9b898a212ea52e283351e521e17871&language=en&page=page&region=US")
    fun getMovies(@Query("page") page: Int): Call<ResponseFormat>
}