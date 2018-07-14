package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.repository.AdRepository
import com.xrojan.lrthubkotlin.repository.entities.Ad
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 15/07/2018.
 */

class AdViewModel(val adRepository: AdRepository){

    fun getAds() : Observable<UIDataArray<List<Ad>>> {
        return adRepository.getAds()
                .map {
                    UIDataArray(it)
                }
    }

}