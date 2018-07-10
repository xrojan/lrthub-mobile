package com.xrojan.lrthubkotlin

import android.app.Application
import android.arch.persistence.room.Room
import com.xrojan.lrthubkotlin.repository.UserRepository
import com.xrojan.lrthubkotlin.repository.api.UserApi
import com.xrojan.lrthubkotlin.repository.db.AppDatabase
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

class App : Application() {


    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var gsonFactory: GsonConverterFactory
        private lateinit var rxJava2CallAdapterFactory: RxJava2CallAdapterFactory

        private lateinit var appDatabase: AppDatabase

        private lateinit var userApi: UserApi
        private lateinit var userRepository: UserRepository
        private lateinit var userViewModel: UserViewModel

        fun injectUserViewModel() = userViewModel
    }

    override fun onCreate() {
        super.onCreate()

        // Networking
        gsonFactory = GsonConverterFactory.create()
        rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
        okHttpClient = OkHttpClient()

        retrofit = Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(resources.getString(R.string.api_base_url))
                .client(okHttpClient)
                .build()

        // Database
        appDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java,
                getString(R.string.database_name)).build()

        // User
        userApi = retrofit.create(UserApi::class.java)
        userRepository = UserRepository(userApi, appDatabase.userDao())
        userViewModel = UserViewModel(userRepository)
    }

}