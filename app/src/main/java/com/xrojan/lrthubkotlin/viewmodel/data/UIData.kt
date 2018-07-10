package com.xrojan.lrthubkotlin.viewmodel.data

import com.xrojan.lrthubkotlin.repository.entities.Request

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

data class UIData<T>(
        val request: Request<T>,
        val error: Throwable? = null
)