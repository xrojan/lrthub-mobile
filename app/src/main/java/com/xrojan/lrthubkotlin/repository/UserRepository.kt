package com.xrojan.lrthubkotlin.repository

import android.util.Log
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.api.UserApi
import com.xrojan.lrthubkotlin.repository.db.UserDao
import com.xrojan.lrthubkotlin.repository.entities.Login
import io.reactivex.Observable

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class UserRepository(val userApi: UserApi, val userDao: UserDao) {
    private var tag: String = UserRepository::class.java.simpleName

    fun loginUser(username: String,
                  password: String): Observable<Request<Login>> {
        return userApi.loginUser(username, password)
                .doOnNext {
                    Log.e(tag, it.toString())
                }
    }

}