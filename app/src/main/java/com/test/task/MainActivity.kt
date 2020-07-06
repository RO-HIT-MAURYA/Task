package com.test.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getMovies(1)
    }

    private fun getMovies(page: Int) {
        val apiInterface: ApiInterface = RetrofitHelper.createService(ApiInterface::class.java)

        val call = apiInterface.getMovies(page)
        call.enqueue(object : Callback<ResponseFormat> {
            override fun onResponse(
                call: Call<ResponseFormat>,
                response: Response<ResponseFormat>
            ) {
                if (response.isSuccessful) {
                    //ArrayList<JsonObject> arrayList = response.body().getList();
                    val jsonArray = response.body()!!.getJsonArray()
                    Log.e("responseIs", response.body().toString() + "")
                }
            }

            override fun onFailure(call: Call<ResponseFormat>, t: Throwable) {
                Log.e("errorIs", t.message)
            }
        })
    }
}