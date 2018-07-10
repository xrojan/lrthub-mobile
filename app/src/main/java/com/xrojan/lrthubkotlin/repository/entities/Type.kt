package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class FeedType(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("is_on_main_page")
        var isOnMainPage: Boolean
)