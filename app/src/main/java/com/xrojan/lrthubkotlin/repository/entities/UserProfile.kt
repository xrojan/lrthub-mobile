package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

data class UserProfile(
        @SerializedName("id")
        val id: Int,
        @SerializedName("user")
        val user: User

)