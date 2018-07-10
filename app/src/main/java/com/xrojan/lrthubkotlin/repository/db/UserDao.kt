package com.xrojan.lrthubkotlin.repository.db

import android.arch.persistence.room.*
import com.xrojan.lrthubkotlin.repository.entities.User


/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}
