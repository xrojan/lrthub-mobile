package com.xrojan.lrthubkotlin.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

@Entity(tableName = "users")
data class UserTable(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "id")
        var uid: Int,
        @ColumnInfo(name = "username")
        var username: String,
        @ColumnInfo(name = "email")
        var email: String,
        @ColumnInfo(name = "first_name")
        var firstName: String,
        @ColumnInfo(name = "last_name")
        var lastName: String,
        @ColumnInfo(name = "token")
        var token: String
)