package com.cs4520.brainflex.dao

import androidx.lifecycle.Transformations

class UserRepository(private val userDao: UserDao) {

    val recent = userDao.getRecent()

    fun add(user: UserEntity) {
        userDao.add(user)
    }

    fun getCurrent(): UserEntity? {
        val curr = userDao.getCurrent()
        if(curr.isEmpty()) {
            println("No current user")
            return null
        }
        return curr[0]
    }
}