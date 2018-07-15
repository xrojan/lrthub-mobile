package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

data class TrainCheckHistory(
        @SerializedName("id")
        val id: Int,
        @SerializedName("no_of_faces")
        val countFaces: Int,
        @SerializedName("no_of_male")
        val countMale: Int,
        @SerializedName("no_of_female")
        val countFemale: Int,
        @SerializedName("created_on")
        val createdOn: String
)