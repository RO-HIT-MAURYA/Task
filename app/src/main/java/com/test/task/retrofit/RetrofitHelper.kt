package com.test.task.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    companion object {
        private val baseUrl = "https://api.themoviedb.org/"

        private lateinit var retrofit: Retrofit
        private val gson = GsonBuilder().create()
        private val httpLoggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttpClientBuilder =
            OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        private val okHttpClient = okHttpClientBuilder.build()

        fun <T> createService(serviceClass: Class<T>): T {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(serviceClass)
        }
    }
}