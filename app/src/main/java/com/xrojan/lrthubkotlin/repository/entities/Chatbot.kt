package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName
import com.xrojan.lrthubkotlin.constants.ASK_MESSAGE_MODE

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

data class Answers(
        @SerializedName("answers")
        val answers: List<Answer>
)

data class Answer(
        @SerializedName("questions")
        val questions: List<String>,
        @SerializedName("answer")
        val answer: String,
        @SerializedName("score")
        val score: Float,
        @SerializedName("id")
        val id: Int
)

data class Question(
        val question: String
)

data class AskMessage(
        val message: String,
        val timestamp: String,
        val sender_mode: ASK_MESSAGE_MODE
)

data class ChatbotQuery(
        val question: String,
        val timestamp: String
)