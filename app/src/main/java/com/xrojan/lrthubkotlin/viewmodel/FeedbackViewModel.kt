package com.xrojan.lrthubkotlin.viewmodel

import android.util.Log
import com.xrojan.lrthubkotlin.repository.FeedbackRepository
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class FeedbackViewModel(val feedbackRepository: FeedbackRepository) {

    private val tag = FeedbackViewModel::class.java.simpleName

    fun getFeedbackConversations(token: String): Observable<UIDataArray<List<FeedbackConversation>>> {
        return feedbackRepository.getFeedbackConversations(token)
                .map {
                    UIDataArray(it)
                }
    }


    fun getFeedbackConversation(token: String, feedbackId: Int): Observable<UIData<FeedbackConversation>> {
        return feedbackRepository.getFeedbackConversation(token, feedbackId)
                .map {
                    UIData(it)
                }
    }

    fun sendFeedback(token: String,
                     id: Int,
                     fullName: String,
                     address: String,
                     contactNumber: String,
                     employeeName: String,
                     incidentDate: String,
                     incidentSubject: String,
                     otherDetails: String): Observable<Request<FeedbackConversation>> {
        return feedbackRepository.sendFeedback(
                token,
                id,
                fullName,
                address,
                contactNumber,
                employeeName,
                incidentDate,
                incidentSubject,
                otherDetails
        )
                .doOnNext {
                    UIData(it)
                }

                .doOnError {
                    Log.e(tag, it.message)
                }
    }
}