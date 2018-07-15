package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.repository.api.TrainCheckApi
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.entities.RequestArray
import com.xrojan.lrthubkotlin.repository.entities.TrainCheckHistory
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

class TrainCheckRepository(val trainCheckApi: TrainCheckApi, val apiKey: String) {
    private val tag = TrainCheckRepository::class.java.simpleName

    fun getTrainCheckFeeds(): Observable<Request<List<TrainCheckHistory>>> {
        return trainCheckApi.getTrainCheckFeeds(apiKey)
                .doOnNext {
                    Log.e(tag, it.result.toString())
                }

                .doOnError {
                    Log.e(tag, it.message)
                }
    }

}