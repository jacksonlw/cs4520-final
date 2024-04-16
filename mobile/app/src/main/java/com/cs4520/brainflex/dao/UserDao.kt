package com.cs4520.brainflex.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY insertedAt DESC LIMIT 1")
    fun getCurrentUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserEntity)
}