package com.xrojan.lrthubkotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.repository.entities.Feed
import kotlinx.android.synthetic.main.item_feed.view.*


/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class FeedAdapter(private val context: Context,
                  private val items: List<Feed>,
                  private val isFeatured: Boolean) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var onItemClick: (Int) -> Unit = {}

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCoverImage: ImageView = view.iv_cover_image
        val tvTitle: TextView = view.tv_title
        val tvContent: TextView = view.tv_content
        val tvDatePosted: TextView = view.tv_date_posted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (isFeatured) {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_featured, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Assign click listener
        holder.itemView.setOnClickListener {
            onItemClick(holder.adapterPosition)
        }

        // Render data
        val feed: Feed = items.get(position)
        if (isFeatured) {
            holder.tvTitle.text = truncate(feed.title, 20, "...")
            holder.tvContent.text = truncate(feed.content, 30, "...")
        } else {
            holder.tvTitle.text = feed.title
            holder.tvContent.text = truncate(feed.content, 100, "...")
        }
        holder.tvDatePosted.text = feed.datePosted

        // Render cover image
        Glide.with(context)
                .load(feed.coverImage)
                .into(holder.ivCoverImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun truncate(str: String, limit: Int, end: String): String {
        if (str.length >= limit) {
            return str.substring(0, limit) + end
        }
        return str
    }
}