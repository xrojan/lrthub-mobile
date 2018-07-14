package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.credentials.Credentials
import com.xrojan.lrthubkotlin.repository.api.ChatbotApi
import com.xrojan.lrthubkotlin.repository.entities.Answers
import com.xrojan.lrthubkotlin.repository.entities.Question
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result.response
import com.google.gson.Gson
import org.json.JSONObject



/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class ChatbotRepository(val chatbotApi: ChatbotApi) {
    private var tag: String = ChatbotRepository::class.java.simpleName

    fun queryQhatbot(question: String): Observable<Answers> {
        return chatbotApi.queryChatbot(Credentials.apiKey, "application/json", Question(question))
                .doOnNext {
                    Log.e(tag, it.answers.toString())
                }
                .doOnError {
                    Log.e(tag, it.message)
                }
    }
}