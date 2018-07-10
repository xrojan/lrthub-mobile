package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class Login(
        @SerializedName("token")
        var token: String,
        @SerializedName("user")
        var user: User
)

data class User(
        @SerializedName("id")
        var id: Int,
        @SerializedName("username")
        var username: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("first_name")
        var firstName: String,
        @SerializedName("last_name")
        var lastName: String,
        @SerializedName("token")
        var token: String
)