package com.cs4520.brainflex.dao

import androidx.lifecycle.LiveData

interface UserRepo {
    val recent: LiveData<List<UserEntity>>
    fun add(user: UserEntity)

    fun getCurrent(): UserEntity?
}