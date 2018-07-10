package com.xrojan.lrthubkotlin.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.xrojan.lrthubkotlin.repository.entities.User
import android.arch.persistence.room.Delete


/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}
