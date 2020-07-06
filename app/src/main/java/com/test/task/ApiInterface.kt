package com.test.task

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface
{
    @GET("3/movie/now_playing?api_key=1d9b898a212ea52e283351e521e17871&language=en")
    fun getMovies(): Call<ResponseFormat>
}