package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.repository.TrainCheckRepository
import com.xrojan.lrthubkotlin.repository.entities.TrainCheckHistory
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

class TrainCheckViewModel(val trainCheckRepository: TrainCheckRepository) {

    fun getTrainCheckFeeds(): Observable<UIData<List<TrainCheckHistory>>> {
        return trainCheckRepository.getTrainCheckFeeds()
                .map {
                    UIData(it)
                }
    }

}