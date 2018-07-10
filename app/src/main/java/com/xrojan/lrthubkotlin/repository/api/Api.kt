package com.xrojan.lrthubkotlin.repository.api

import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.repository.entities.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

interface UserApi {
    @FormUrlEncoded
    @POST("/api/v1/token/generate")
    fun loginUser(@Field("username") username: String,
                  @Field("password") password: String): Observable<Request<User>>
}