package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.repository.api.FeedbackApi
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import com.xrojan.lrthubkotlin.repository.entities.RequestArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class FeedbackRepository(val feedbackApi: FeedbackApi, val apiKey: String) {
    private var tag: String = FeedbackRepository::class.java.simpleName
    fun getFeedbackConversations(token: String): Observable<RequestArray<List<FeedbackConversation>>> {
        return feedbackApi.getFeedbackConversations(token)
                .doOnNext {
                    Log.e(tag, it.toString())
                }

                .doOnError {
                    Log.e(tag, it.message)
                }
    }
}