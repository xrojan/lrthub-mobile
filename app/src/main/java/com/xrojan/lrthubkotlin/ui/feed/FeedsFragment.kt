package com.xrojan.lrthubkotlin.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.adapters.FeedAdapter
import com.xrojan.lrthubkotlin.constants.TAG
import com.xrojan.lrthubkotlin.fragments.BaseFragment
import com.xrojan.lrthubkotlin.repository.entities.Feed
import com.xrojan.lrthubkotlin.viewmodel.FeedViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feeds_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class FeedsFragment : BaseFragment() {
    private val feedViewModel: FeedViewModel = App.injectFeedViewModel()
    private lateinit var feedDetailDialogFragment: FeedDetailDialogFragment

    companion object {
        fun newInstance() = FeedsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feeds_fragment, container, false)
    }

    private fun initComponents() {
        subscribe(feedViewModel.getFeeds(true)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    showFeaturedFeeds(it)
                }, {
//                    showError(tag!!, it.message.toString())
                    doAsync {
                        uiThread {
                            pb_loading.visibility = View.GONE
                        }
                    }
                }))

        subscribe(feedViewModel.getFeeds(false)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    showFeeds(it)
                }, {
//                    showError(tag!!, it.message.toString())
                    doAsync {
                        uiThread {
                            pb_loading.visibility = View.GONE
                        }
                    }
                }))
    }

    private fun showFeaturedFeeds(data: UIDataArray<List<Feed>>) {
        Log.e(tag!!, data.toString())
        doAsync {
            uiThread {
                val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                rv_featured_feeds.layoutManager = layoutManager

                val feedAdapter = FeedAdapter(context!!, data.request.result, true)
                feedAdapter.onItemClick = {
                    feedDetailDialogFragment = FeedDetailDialogFragment()
                    feedDetailDialogFragment.show(fragmentManager, TAG.FEED_DETAIL, data.request.result[it])
                }
//                val snapHelper: SnapHelper()
                val snapHelper: SnapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rv_featured_feeds)
                rv_featured_feeds.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            val centerView = snapHelper.findSnapView(layoutManager)
                            val int = layoutManager.getPosition(centerView)
                        }
                    }
                })
//                rv_featured_feeds.

                rv_featured_feeds.adapter = feedAdapter
                pb_loading.visibility = View.GONE
            }
        }
    }

    private fun showFeeds(data: UIDataArray<List<Feed>>) {
        Log.e(tag!!, data.toString())
        doAsync {
            uiThread {
                rv_feeds.layoutManager = LinearLayoutManager(context)

                val feedAdapter = FeedAdapter(context!!, data.request.result, false)
                feedAdapter.onItemClick = {
                    feedDetailDialogFragment = FeedDetailDialogFragment()
                    feedDetailDialogFragment.show(fragmentManager, TAG.FEED_DETAIL, data.request.result[it])
                }

                rv_feeds.adapter = feedAdapter
                pb_loading.visibility = View.GONE
            }
        }
    }

}
