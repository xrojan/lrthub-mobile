package com.xrojan.lrthubkotlin.providers

import android.content.SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
import android.content.SearchRecentSuggestionsProvider



/**
 * Created by Joshua de Guzman on 11/07/2018.
 */

class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        val AUTHORITY = "com.xrojan.lrthubkotlin.providers.SuggestionProvider"
        val MODE = DATABASE_MODE_QUERIES
    }
}