package com.cs4520.brainflex

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.api.requests.LoginRequestBody
import com.cs4520.brainflex.dao.UserDao
import com.cs4520.brainflex.dao.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime

class LogInViewModel(private val apiClient: ApiClient, private val userDao: UserDao) : ViewModel() {
    private val _loginResponseEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val loginResponseEvent = _loginResponseEvent.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val body = LoginRequestBody(username)
                val res = apiClient.login(body)
                if (res.isSuccessful) {
                    _loginResponseEvent.tryEmit(true)
                    userDao.addUser(UserEntity(username, OffsetDateTime.now()))
                } else {
                    _loginResponseEvent.tryEmit(false)
                }
            }
        }

    }

}