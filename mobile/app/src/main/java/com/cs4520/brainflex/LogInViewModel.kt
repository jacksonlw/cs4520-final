package com.cs4520.brainflex

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.api.requests.LoginRequestBody
import com.cs4520.brainflex.dao.UserDao
import com.cs4520.brainflex.dao.UserEntity
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.dto.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {

    val recentUsernames = Transformations.map(userRepo.recent) { entities ->
        entities.map { it.username }
    }

    private val _loginResponseEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val loginResponseEvent = _loginResponseEvent.asSharedFlow()

    fun login(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val body = LoginRequestBody(username)
                val res = apiClient.login(body)
                if (!res.isSuccessful) {
                    _loginResponseEvent.tryEmit(false)
                    return@withContext
                }

                _loginResponseEvent.tryEmit(true)
                userRepo.add(UserEntity(username))
            }
        }

    }

}