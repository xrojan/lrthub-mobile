package com.xrojan.lrthubkotlin.repository

import android.annotation.SuppressLint
import android.util.Log
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.api.UserApi
import com.xrojan.lrthubkotlin.repository.db.UserDao
import com.xrojan.lrthubkotlin.repository.entities.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class UserRepository(val userApi: UserApi, val userDao: UserDao) {
    private var tag: String = UserRepository::class.java.simpleName

    /**
     * Requests for user login verification
     * @return Login model with the generated token and user credentials
     */
    fun loginUser(username: String,
                  password: String): Observable<Request<User>> {
        return userApi.loginUser(username, password)
                .doOnNext {
                    storeUserInDb(it.result)
                    Log.e(tag, it.result.toString())
                }
    }

    fun registerUser(username: String, password: String, email: String): Observable<Request<User>> {
        return userApi.registerUser(username, password, email)
                .doOnNext {
                    // Register User
                    Log.e(tag, it.result.toString())
                }
    }

    /**
     * Stores users in the local database
     * @param user
     */
    @SuppressLint("CheckResult")
    private fun storeUserInDb(user: User) {
        Observable.fromCallable { userDao.insert(user) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    userDao.deleteAll()
                    userDao.insert(user)
                }
    }
}