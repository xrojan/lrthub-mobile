package com.xrojan.lrthubkotlin.repository.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */


@Entity(tableName = "users")
data class User(
        @PrimaryKey
        val uid: Int,
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: Int,
        @ColumnInfo(name = "username")
        @SerializedName("username")
        var username: String,
        @ColumnInfo(name = "email")
        @SerializedName("email")
        var email: String,
        @ColumnInfo(name = "first_name")
        @SerializedName("first_name")
        var firstName: String,
        @ColumnInfo(name = "last_name")
        @SerializedName("last_name")
        var lastName: String,
        @ColumnInfo(name = "token")
        @SerializedName("token")
        var token: String
)