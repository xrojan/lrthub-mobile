package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.repository.FeedRepository
import com.xrojan.lrthubkotlin.repository.entities.Feed
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */


class FeedViewModel(private val feedRepository: FeedRepository) {
    private val tag: String = FeedViewModel::class.java.simpleName

    fun getFeeds(search: String): Observable<UIDataArray<List<Feed>>> {
        return feedRepository.getFeeds(search)
                .map {
                    UIDataArray(it)
                }
    }

    fun getFeeds(isFeatured: Boolean): Observable<UIDataArray<List<Feed>>> {
        return feedRepository.getFeeds(isFeatured)
                .map {
                    UIDataArray(it)
                }
    }

}