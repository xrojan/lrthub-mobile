package com.xrojan.lrthubkotlin.repository.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

data class Feed(
        @SerializedName("id")
        var id: Int,
        @SerializedName("type_id")
        var typeId: Int,
        @SerializedName("cover_image")
        var coverImage: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("content")
        var content: String,
        @SerializedName("date_posted")
        var datePosted: String,
        @SerializedName("is_deleted")
        var isDeleted: Boolean
)