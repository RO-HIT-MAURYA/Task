package com.test.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.test.task.retrofit.ApiInterface
import com.test.task.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val jsonList: ArrayList<JsonObject> = ArrayList()
    private val recyclerViewAdapter: RecyclerViewAdapter =
        RecyclerViewAdapter(jsonList, object : ReloadCallBack {
            override fun reload() {
                page++
                getMovies()
            }
        })
    private var page = 1
    private var loadMore = true//will be false when received array is empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Now Playing"

        findViewById<RecyclerView>(R.id.recyclerView).adapter = recyclerViewAdapter


        getMovies()
    }

    private fun getMovies() {

        if (!loadMore)
            return

        val apiInterface: ApiInterface = RetrofitHelper.createService(
            ApiInterface::class.java
        )

        val call = apiInterface.getMovies(page)
        call.enqueue(object : Callback<ResponseFormat> {
            override fun onResponse(
                call: Call<ResponseFormat>,
                response: Response<ResponseFormat>
            ) {
                if (response.isSuccessful) {
                    val jsonArray = response.body()!!.getJsonArray()
                    if (jsonArray.size() == 0)
                        loadMore = false
                    Log.v("arrayLengthIs", jsonArray.size().toString())
                    //ArrayList<OBJECT> yourArray = new Gson().fromJson(myjsonarray.toString(), new TypeToken<List<OBJECT>>(){}.getType());
                    for (jsonElement: JsonElement in jsonArray) {
                        if (jsonElement.isJsonNull)
                            continue
                        jsonList.add(jsonElement as JsonObject)
                    }
                    Log.v("listLengthIs", jsonList.size.toString())
                    recyclerViewAdapter.notifyDataSetChanged()


                    Log.e("responseIs", response.body().toString() + "")
                }
            }

            override fun onFailure(call: Call<ResponseFormat>, t: Throwable) {
                Log.e("errorIs", t.message)
            }
        })
    }

    public interface ReloadCallBack {
        fun reload()
    }
}