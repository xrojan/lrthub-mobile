package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.repository.FeedbackRepository
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class FeedbackViewModel(val feedbackRepository: FeedbackRepository) {

    fun getFeedbackConversations(token: String): Observable<UIDataArray<List<FeedbackConversation>>> {
        return feedbackRepository.getFeedbackConversations(token)
                .map {
                    UIDataArray(it)
                }
    }

}