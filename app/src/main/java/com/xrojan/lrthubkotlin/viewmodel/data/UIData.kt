package com.xrojan.lrthubkotlin.viewmodel.data

import com.xrojan.lrthubkotlin.repository.entities.Answer
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.entities.RequestArray

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

data class UIData<T>(
        val request: Request<T>,
        val error: Throwable? = null
)

data class UIDataArray<T>(
        val request: RequestArray<T>,
        val error: Throwable? = null
)

data class ChatbotData(
        val answer: Answer
)