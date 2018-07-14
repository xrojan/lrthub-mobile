package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.repository.api.AdApi
import com.xrojan.lrthubkotlin.repository.entities.Ad
import com.xrojan.lrthubkotlin.repository.entities.RequestArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

class AdRepository(val adApi: AdApi, val apiKey: String) {
    private val tag = AdRepository::class.java.simpleName

    fun getAds(): Observable<RequestArray<List<Ad>>> {
        return adApi.getAds(apiKey)
                .doOnNext {
                    Log.e(tag, it.result.toString())
                }

                .doOnError {
                    Log.e(tag, it.message)
                }
    }

}