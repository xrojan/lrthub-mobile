package com.xrojan.lrthubkotlin.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.xrojan.lrthubkotlin.repository.entities.User

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}