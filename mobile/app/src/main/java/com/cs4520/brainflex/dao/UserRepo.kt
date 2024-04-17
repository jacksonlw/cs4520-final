package com.cs4520.brainflex.dao

interface UserRepo {

    fun add(user: UserEntity)

    fun getCurrent(): UserEntity?
}