package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class Request<T>(
        @SerializedName("status_code")
        var statusCode: Int,
        @SerializedName("message")
        var message: String,
        @SerializedName("result")
        var result: T
)