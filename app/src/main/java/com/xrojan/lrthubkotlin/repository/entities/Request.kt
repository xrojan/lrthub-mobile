package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class Request<T>(
        @SerializedName("status_code")
        val statusCode: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: T
)

data class RequestArray<T>(
        @SerializedName("status_code")
        val statusCode: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("links")
        val links: Links,
        @SerializedName("page_count")
        val pageCount: Int,
        @SerializedName("results")
        val result: T
)