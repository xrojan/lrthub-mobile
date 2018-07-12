package com.xrojan.lrthubkotlin.ui.feed

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.repository.entities.Feed
import kotlinx.android.synthetic.main.feed_detail_dialog_fragment.*

/**
 * Created by Joshua de Guzman on 11/07/2018.
 */

class FeedDetailDialogFragment : DialogFragment() {

    private lateinit var feed: Feed

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_detail_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderUI(this.feed)
    }

    override fun onStart() {
        super.onStart()
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, theme)
        dialog.window!!.setWindowAnimations(R.style.DialogAnimationRtlLtr)
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    fun show(manager: FragmentManager?, tag: String?, feed: Feed) {
        super.show(manager, tag)
        this.feed = feed
        Log.e(tag, feed.toString())
    }

    private fun renderUI(feed: Feed) {
        // Render details
        collapsing_toolbar.title = feed.title

        // Render cover image
        Glide.with(context!!)
                .load(feed.coverImage)
                .into(iv_cover_image)
    }
}