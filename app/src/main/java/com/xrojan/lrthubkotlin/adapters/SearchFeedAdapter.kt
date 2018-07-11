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

class SearchFeedAdapter(private val context: Context,
                        private val items: List<Feed>) : RecyclerView.Adapter<SearchFeedAdapter.ViewHolder>() {

    private var feedItems: MutableList<Feed> = ArrayList()
    private var feedItemsSearch: MutableList<Feed> = ArrayList()
    private var itemsCount: Int = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCoverImage: ImageView = view.iv_cover_image
        val tvTitle: TextView = view.tv_title
        val tvContent: TextView = view.tv_content
        val tvDatePosted: TextView = view.tv_date_posted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        feedItems = items as MutableList<Feed>
        itemsCount = feedItems.count()
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed: Feed = feedItems[position]
        holder.tvTitle.text = truncate(feed.title, 20, "...")
        holder.tvContent.text = truncate(feed.content, 30, "...")
        holder.tvDatePosted.text = feed.datePosted

        Glide.with(context)
                .load(feed.coverImage)
                .into(holder.ivCoverImage)
    }

    override fun getItemCount(): Int {
        return itemsCount
    }

    private fun truncate(str: String, limit: Int, end: String): String {
        if (str.length >= limit) {
            return str.substring(0, limit) + end
        }
        return str
    }

    /**
     * Filters the feed items based on the query
     * This method can filter the list based on the title or content
     * @param query: String parameter passed by search view
     */
    fun filter(query: String) {
        feedItemsSearch = mutableListOf()
        for (item: Feed in items) {
            if (item.title.toLowerCase().contains(query.toLowerCase()) || item.content.toLowerCase().contains(query.toLowerCase())) {
                feedItemsSearch.add(item)
            }
        }
        feedItems = mutableListOf()
        feedItems.addAll(feedItemsSearch)
        itemsCount = feedItems.count()
        notifyDataSetChanged()
    }

    /**
     * Resets view to original feed list
     */
    fun resetFilter() {
        feedItemsSearch = mutableListOf()
        feedItems = mutableListOf()
        feedItems.addAll(items)
        itemsCount = feedItems.count()
        notifyDataSetChanged()
    }

}