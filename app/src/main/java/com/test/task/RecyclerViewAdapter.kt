package com.test.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.movie_item.view.*

class RecyclerViewAdapter(private val arrayList: ArrayList<JsonObject>) : RecyclerView.Adapter<RecyclerViewAdapter.InnerClass>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InnerClass {
        return InnerClass(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: InnerClass, position: Int) {

    }

    class InnerClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageView
        val titleTextView = itemView.titleTextView
        val bodyTextView = itemView.bodyTextView
        val progressBar = itemView.progressBar
        val ratingTextView = itemView.ratingTextView
        val dateTextureView = itemView.dateTextView
    }
}