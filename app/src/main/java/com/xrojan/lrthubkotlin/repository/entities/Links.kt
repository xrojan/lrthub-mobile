package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class Links(
        @SerializedName("next")
        var next: Int,
        @SerializedName("previous")
        var previous: Int
)