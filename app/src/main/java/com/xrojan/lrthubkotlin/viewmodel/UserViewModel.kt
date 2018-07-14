package com.xrojan.lrthubkotlin.viewmodel

import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import com.xrojan.lrthubkotlin.repository.UserRepository
import com.xrojan.lrthubkotlin.repository.entities.Feed
import com.xrojan.lrthubkotlin.repository.entities.User
import com.xrojan.lrthubkotlin.repository.entities.UserProfile
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

class UserViewModel(private val userRepository: UserRepository) {
    private val tag: String = UserViewModel::class.java.simpleName

    fun loginUser(username: String, password: String): Observable<UIData<User>> {
        return userRepository.loginUser(username, password)
                .map {
                    UIData(it)
                }
    }

    fun registerUser(username: String, password: String, email: String): Observable<UIData<User>> {
        return userRepository.registerUser(username, password, email)
                .map {
                    UIData(it)
                }
    }

    fun getUserDetail(token: String, userId: Int): Observable<UIDataArray<List<UserProfile>>>? {
        return userRepository.getUserDetail(token, userId)
                .map {
                    UIDataArray(it)
                }
    }

    fun getUserLocalData(): Observable<List<User>>{
        return userRepository.getUserCredentials()
                .map {
                    it
                }
    }


}