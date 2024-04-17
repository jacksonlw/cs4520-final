package com.cs4520.brainflex.dao

import androidx.lifecycle.Transformations

class UserRepository(private val userDao: UserDao) {
    val current = Transformations.map(userDao.getCurrent()) {
        if (it.isEmpty()) {
            return@map null
        }
        return@map it[0]
    }

    val recent = userDao.getRecent()

    fun add(user: UserEntity) {
        userDao.add(user)
    }
}