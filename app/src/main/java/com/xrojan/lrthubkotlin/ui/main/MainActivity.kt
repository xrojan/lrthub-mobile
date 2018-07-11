package com.xrojan.lrthubkotlin.ui.main

import android.os.Bundle
import android.view.Menu
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.activities.BaseActivity
import com.xrojan.lrthubkotlin.ui.feed.FeedsFragment
import com.xrojan.lrthubkotlin.ui.feedback.FeedbackFragment
import com.xrojan.lrthubkotlin.ui.settings.SettingsFragment
import com.xrojan.lrthubkotlin.ui.traincheck.TraincheckFragment
import kotlinx.android.synthetic.main.main_activity.*
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.widget.SearchView
import com.xrojan.lrthubkotlin.activities.SearchableActivity


class MainActivity : BaseActivity() {
    private lateinit var feedsFragment: FeedsFragment
    private lateinit var traincheckFragment: TraincheckFragment
    private lateinit var feedbackFragment: FeedbackFragment
    private lateinit var settingsFragment: SettingsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            feedsFragment = FeedsFragment.newInstance()
            traincheckFragment = TraincheckFragment.newInstance()
            feedbackFragment = FeedbackFragment.newInstance()
            settingsFragment = SettingsFragment.newInstance()
            initComponents()
        }
    }

    private fun initComponents() {
        bnv_dashboard.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_feeds -> {
                    loadFragment(R.id.fl_container, feedsFragment, feedsFragment::class.java.simpleName)
                }
                R.id.nav_traincheck -> {
                    loadFragment(R.id.fl_container, traincheckFragment, traincheckFragment::class.java.simpleName)
                }
                R.id.nav_feedback -> {
                    loadFragment(R.id.fl_container, feedbackFragment, feedbackFragment::class.java.simpleName)
                }
                R.id.nav_settings -> {
                    loadFragment(R.id.fl_container, settingsFragment, settingsFragment::class.java.simpleName)
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
        return true
    }
}
