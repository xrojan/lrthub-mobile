package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

data class Ad(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("interstitial_image")
        val interstitialImage: String,
        @SerializedName("banner_ad_image")
        val bannerAdImage: String,
        @SerializedName("video_url")
        val videoUrl: String,
        @SerializedName("ad_url")
        val adUrl: String
)