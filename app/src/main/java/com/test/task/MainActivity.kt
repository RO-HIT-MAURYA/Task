package com.test.task

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.test.task.databinding.ActivityMainBinding
import com.test.task.retrofit.ApiInterface
import com.test.task.retrofit.Internet
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
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.adapter = recyclerViewAdapter
        binding.shimmerFrameLayout.startShimmerAnimation()

        if (!Internet(this).isAvailable()) {
            Snackbar.make(binding.progressBar, R.string.no_internet, Snackbar.LENGTH_INDEFINITE).show()
            return
        }

        getMovies()
    }

    private fun getMovies() {

        if (!loadMore)
            return

        binding.progressBar.visibility = View.VISIBLE

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
                    //ArrayList<OBJECT> yourArray = new Gson().fromJson(myjsonarray.toString(), new TypeToken<List<OBJECT>>(){}.getType());
                    for (jsonElement: JsonElement in jsonArray) {
                        if (jsonElement.isJsonNull)
                            continue
                        jsonList.add(jsonElement as JsonObject)
                    }
                    recyclerViewAdapter.notifyDataSetChanged()

                    handleLoaders()
                    //Log.e("responseIs", response.body().toString() + "")
                }
            }

            override fun onFailure(call: Call<ResponseFormat>, t: Throwable) {
                Log.e("errorIs", t.message)
                Snackbar.make(binding.progressBar, t.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun handleLoaders() {
        binding.progressBar.visibility = View.GONE

        if (binding.shimmerFrameLayout.isAnimationStarted) {
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }

    interface ReloadCallBack {
        fun reload()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater

        inflater.inflate(
            R.menu.menu_item,
            menu
        )

        return super.onCreateOptionsMenu(menu)
    }
}