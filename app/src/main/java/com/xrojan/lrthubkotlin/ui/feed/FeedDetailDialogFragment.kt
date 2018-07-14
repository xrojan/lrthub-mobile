package com.xrojan.lrthubkotlin.ui.feed

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.constants.EVENT_TRACKER
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
        // Track events
        Answers.getInstance().logContentView(ContentViewEvent()
                .putCustomAttribute(EVENT_TRACKER.FEED, feed.title)
                .putCustomAttribute(EVENT_TRACKER.FEED_CATEGORY, feed.feedType.name))

        if (feed.isFeatured) {
            Answers.getInstance().logContentView(ContentViewEvent()
                    .putCustomAttribute(EVENT_TRACKER.FEED_FEATURED, feed.title))
        } else {
            Answers.getInstance().logContentView(ContentViewEvent()
                    .putCustomAttribute(EVENT_TRACKER.FEED_NON_FEATURED, feed.title))
        }

        // Setup listeners
        sv_main.viewTreeObserver.addOnScrollChangedListener({
            val rect = Rect()
            sv_main.getHitRect(rect)
            if (v_bounds.getLocalVisibleRect(rect)) {
                ib_scroll_to_top.visibility = View.GONE
            } else {
                ib_scroll_to_top.visibility = View.VISIBLE
            }
        })

        ib_scroll_to_top.visibility = View.GONE
        ib_scroll_to_top.setOnClickListener { onScrollToTop() }

        // Render details
        collapsing_toolbar.title = feed.title
        tv_title.text = feed.title
        tv_content.text = feed.content
        tv_date_posted.text = "POSTED ON " + feed.datePosted


        // Render cover image
        Glide.with(context!!)
                .load(feed.coverImage)
                .into(iv_cover_image)

    }

    private fun onScrollToTop() {
        sv_main.fling(0)
        sv_main.fullScroll(View.FOCUS_UP)
        sv_main.scrollTo(0, 0)
        ib_scroll_to_top.visibility = View.GONE
    }
}