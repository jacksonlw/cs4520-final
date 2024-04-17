package com.cs4520.brainflex.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY insertedAt DESC LIMIT 1")
    fun getCurrent(): List<UserEntity>

    @Query("SELECT * FROM users ORDER BY insertedAt DESC LIMIT 3")
    fun getRecent(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: UserEntity)
}