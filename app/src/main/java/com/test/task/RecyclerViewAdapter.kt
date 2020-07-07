package com.test.task

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(
    private val jsonList: ArrayList<JsonObject>,
    private val reloadCallBack: MainActivity.ReloadCallBack
) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return jsonList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val jsonObject = jsonList[position]
        //Log.e("jsonObjectIs",jsonObject.toString())

        try {
            Picasso.get()
                .load(CommonParameters.imageUrl + jsonObject.get(CommonParameters.poster_path).asString)
                .placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView)
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }

        holder.titleTextView.text = jsonObject.get(CommonParameters.original_title).asString
        holder.bodyTextView.text = jsonObject.get(CommonParameters.overview).asString

        val percent = getPercent(jsonObject.get(CommonParameters.vote_average).asFloat)
        holder.ratingTextView.text = "$percent%"

        if (percent < 60)
            holder.progressBar.secondaryProgressTintList =
                ColorStateList.valueOf(Color.parseColor("#FF0000"))
        else if (percent in 60..79)
            holder.progressBar.secondaryProgressTintList =
                ColorStateList.valueOf(Color.parseColor("#FFEB3B"))
        else
            holder.progressBar.secondaryProgressTintList =
                ColorStateList.valueOf(Color.parseColor("#00FF00"))

        holder.progressBar.secondaryProgress = percent

        holder.bodyTextView.text = jsonObject.get(CommonParameters.overview).asString

        holder.dateTextView.text =
            changeDateFormat(jsonObject.get(CommonParameters.release_date).asString)

        if (position == jsonList.size - 1)
            reloadCallBack.reload()
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.imageView
        val titleTextView: TextView = itemView.titleTextView
        val bodyTextView: TextView = itemView.bodyTextView
        val progressBar: ProgressBar = itemView.progressBar
        val ratingTextView: TextView = itemView.ratingTextView
        val dateTextView: TextView = itemView.dateTextView
    }

    companion object {
        fun changeDateFormat(string: String): CharSequence {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = simpleDateFormat.parse(string)

            simpleDateFormat = SimpleDateFormat("MMM dd, yyyy")
            val str = simpleDateFormat.format(date)
            return str
        }

        fun getPercent(asFloat: Float): Int {
            return (asFloat * 10).toInt()
        }
    }
}