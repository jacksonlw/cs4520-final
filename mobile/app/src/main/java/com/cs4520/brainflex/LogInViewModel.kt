package com.cs4520.brainflex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.api.requests.LoginRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel(private val apiClient: ApiClient) : ViewModel() {
    private val _loginResponseEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val loginResponseEvent = _loginResponseEvent.asSharedFlow()

    fun login(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val body = LoginRequestBody(username)
                val res = apiClient.login(body)
                if(res.isSuccessful) {
                    _loginResponseEvent.tryEmit(true)
                } else {
                    _loginResponseEvent.tryEmit(false)
                }
            }
        }

    }

}