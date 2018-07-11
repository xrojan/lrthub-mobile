package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.repository.api.FeedApi
import com.xrojan.lrthubkotlin.repository.entities.Feed
import com.xrojan.lrthubkotlin.repository.entities.RequestArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class FeedRepository(val feedApi: FeedApi, val apiKey: String) {
    private var tag: String = FeedRepository::class.java.simpleName

    /**
     * Returns feed list request with search params
     */
    fun getFeeds(search: String): Observable<RequestArray<List<Feed>>> {
        return feedApi.getFeeds(apiKey, search)
                .doOnNext {
                    Log.e(tag, it.toString())
                    Log.e(tag, it.result.toString())
                }
                .doOnError {
                    Log.e(tag, it.toString())
                    Log.e(tag, it.message)
                }
    }

    /**
     * Returns feed list request
     */
    fun getFeeds(isFeatured: Boolean): Observable<RequestArray<List<Feed>>> {
        return feedApi.getFeeds(apiKey, isFeatured)
                .doOnNext {
                    Log.e(tag, it.toString())
                    Log.e(tag, it.result.toString())
                }
                .doOnError {
                    Log.e(tag, it.toString())
                    Log.e(tag, it.message)
                }
    }
}