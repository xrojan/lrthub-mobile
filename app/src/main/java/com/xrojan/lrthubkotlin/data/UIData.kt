package com.xrojan.lrthubkotlin.data

import com.google.gson.annotations.SerializedName
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.entities.User

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

data class UIData<T>(
        val request: Request<T>,
        val error: Throwable? = null
)