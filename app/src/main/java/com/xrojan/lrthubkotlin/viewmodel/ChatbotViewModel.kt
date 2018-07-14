package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.repository.ChatbotRepository
import com.xrojan.lrthubkotlin.viewmodel.data.ChatbotData
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class ChatbotViewModel(private val chatbotRepository: ChatbotRepository) {

    fun queryChatbot(question: String): Observable<ChatbotData> {
        return chatbotRepository.queryQhatbot(question)
                .map {
                    // Assume that the first answer will always be the best answer available as a response to the query
                    ChatbotData(it.answers[0])
                }
    }
}