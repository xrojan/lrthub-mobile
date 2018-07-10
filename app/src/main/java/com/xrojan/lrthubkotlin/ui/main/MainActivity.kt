package com.xrojan.lrthubkotlin.ui.main

import android.os.Bundle
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.activities.BaseActivity
import com.xrojan.lrthubkotlin.ui.news.NewsFragment
import com.xrojan.lrthubkotlin.ui.feed.FeedsFragment
import com.xrojan.lrthubkotlin.ui.feedback.FeedbackFragment
import com.xrojan.lrthubkotlin.ui.settings.SettingsFragment
import com.xrojan.lrthubkotlin.ui.traincheck.TraincheckFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity() {

    private lateinit var newsFragment: NewsFragment
    private lateinit var feedsFragment: FeedsFragment
    private lateinit var traincheckFragment: TraincheckFragment
    private lateinit var feedbackFragment: FeedbackFragment
    private lateinit var settingsFragment: SettingsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            newsFragment = NewsFragment.newInstance()
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
                R.id.nav_news -> {
                    loadFragment(R.id.fl_container, newsFragment)
                }
                R.id.nav_feeds -> {
                    loadFragment(R.id.fl_container, feedsFragment)
                }
                R.id.nav_traincheck -> {
                    loadFragment(R.id.fl_container, traincheckFragment)
                }
                R.id.nav_feedback -> {
                    loadFragment(R.id.fl_container, feedbackFragment)
                }
                R.id.nav_settings -> {
                    loadFragment(R.id.fl_container, settingsFragment)
                }
            }
            true
        }

        // Load initial view
        bnv_dashboard.selectedItemId = R.id.nav_traincheck
    }
}
