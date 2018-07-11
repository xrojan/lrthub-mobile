package com.xrojan.lrthubkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.app.SearchManager


/**
 * Created by Joshua de Guzman on 11/07/2018.
 */

class SearchableActivity : BaseActivity() {
    private val tag = SearchableActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            supportActionBar!!.title = query
        }
    }
}

