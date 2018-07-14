package com.xrojan.lrthubkotlin.ui.main

import android.os.Bundle
import android.view.Menu
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.activities.BaseActivity
import com.xrojan.lrthubkotlin.ui.feed.FeedsFragment
import com.xrojan.lrthubkotlin.ui.feedback.FeedbackFragment
import com.xrojan.lrthubkotlin.ui.profile.ProfileFragment
import com.xrojan.lrthubkotlin.ui.traincheck.TraincheckFragment
import kotlinx.android.synthetic.main.main_activity.*
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.bumptech.glide.Glide
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.activities.SearchableActivity
import com.xrojan.lrthubkotlin.adapters.SearchFeedAdapter
import com.xrojan.lrthubkotlin.constants.EVENT_TRACKER
import com.xrojan.lrthubkotlin.repository.entities.Ad
import com.xrojan.lrthubkotlin.repository.entities.Feed
import com.xrojan.lrthubkotlin.ui.chatbot.ChatbotFragment
import com.xrojan.lrthubkotlin.viewmodel.AdViewModel
import com.xrojan.lrthubkotlin.viewmodel.FeedViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_activity.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.uiThread
import java.util.*


class MainActivity : BaseActivity() {
    private val tag = MainActivity::class.java.simpleName
    private val feedViewModel: FeedViewModel = App.injectFeedViewModel()
    private val adViewModel: AdViewModel = App.injectAdViewModel()

    private lateinit var feedsFragment: FeedsFragment
    private lateinit var traincheckFragment: TraincheckFragment
    private lateinit var chatbotFragment: ChatbotFragment
    private lateinit var feedbackFragment: FeedbackFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var searchFeedAdapter: SearchFeedAdapter
    private lateinit var adList: List<Ad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            feedsFragment = FeedsFragment.newInstance()
            chatbotFragment = ChatbotFragment.newInstance()
            traincheckFragment = TraincheckFragment.newInstance()
            feedbackFragment = FeedbackFragment.newInstance()
            profileFragment = ProfileFragment.newInstance()
            initComponents()
            subscribe(feedViewModel.getFeeds("")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .subscribe({
                        populateFeedItemSearch(it)
                    }, {
                        Log.e(tag, it.message)
                    }))
        }
    }

    private fun initComponents() {
        bnv_dashboard.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_feeds -> {
                    loadFragment(R.id.fl_container, feedsFragment, feedsFragment::class.java.simpleName)
                }
                R.id.nav_ask -> {
                    loadFragment(R.id.fl_container, chatbotFragment, chatbotFragment::class.java.simpleName)
                }
                R.id.nav_traincheck -> {
                    loadFragment(R.id.fl_container, traincheckFragment, traincheckFragment::class.java.simpleName)
                }
                R.id.nav_feedback -> {
                    loadFragment(R.id.fl_container, feedbackFragment, feedbackFragment::class.java.simpleName)
                }
                R.id.nav_settings -> {
                    loadFragment(R.id.fl_container, profileFragment, profileFragment::class.java.simpleName)
                }
            }
            true
        }

        // Load initial view
        bnv_dashboard.selectedItemId = R.id.nav_feeds
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.nav_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(ComponentName(this, SearchableActivity::class.java)))
        searchView.queryHint = getString(R.string.search_hint)
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.trim() != "") {
                    rv_feeds_search.visibility = View.VISIBLE
                    searchFeedAdapter.filter(newText)
                } else {
                    searchFeedAdapter.resetFilter()
                    rv_feeds_search.visibility = View.GONE
                }
                return true
            }
        })
        searchView.setOnQueryTextFocusChangeListener { v, newViewFocus ->
            if (!newViewFocus) {
                // Clear filter
                searchFeedAdapter.resetFilter()
                rv_feeds_search.visibility = View.GONE
            }
        }
        searchView.setOnCloseListener {
            searchFeedAdapter.resetFilter()
            rv_feeds_search.visibility = View.GONE
            true
        }
        searchManager.setOnCancelListener {
            searchView.queryHint = getString(R.string.search_hint)
            searchFeedAdapter.resetFilter()
            rv_feeds_search.visibility = View.GONE
        }
        return true
    }

    private fun populateFeedItemSearch(data: UIDataArray<List<Feed>>) {
        doAsync {
            uiThread {
                rv_feeds_search.layoutManager = LinearLayoutManager(applicationContext)
                searchFeedAdapter = SearchFeedAdapter(applicationContext, data.request.result)
                rv_feeds_search.adapter = searchFeedAdapter
            }
        }

        // Request for ads after feed items
        subscribe(adViewModel.getAds()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    populateAds(it)
                }, {
                    Log.e(tag, it.message)
                }))
    }

    private fun populateAds(data: UIDataArray<List<Ad>>) {
        doAsync {
            uiThread {
                // Run ads here
                iv_banner_ad_view.visibility = View.VISIBLE

                // FIXME
                // Randomize ads
                val rand = Random()

                // Get target ads
                val ad: Ad = data.request.result[rand.nextInt(data.request.result.size)]

                // Render banner image
                Glide.with(applicationContext)
                        .load(ad.bannerAdImage)
                        .into(iv_banner_ad_view.iv_banner_ad_view)

                iv_banner_ad_view.setOnClickListener {
                    // Add event trackers
                    Answers.getInstance().logContentView(ContentViewEvent()
                            .putCustomAttribute(EVENT_TRACKER.ADS, ad.title))

                    // Add url intent
                    if (!ad.adUrl.isEmpty()) {
                        val url = ad.adUrl
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }
                }
            }
        }
    }
}
