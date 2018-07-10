package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.data.UIData
import com.xrojan.lrthubkotlin.repository.UserRepository
import com.xrojan.lrthubkotlin.repository.entities.Login
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

class UserViewModel(private val userRepository: UserRepository) {
    private val tag: String = UserViewModel::class.java.simpleName

    fun loginUser(username: String, password: String): Observable<UIData<Login>> {
        return userRepository.loginUser(username, password)
                .map {
                    UIData(it)
                }
    }

}