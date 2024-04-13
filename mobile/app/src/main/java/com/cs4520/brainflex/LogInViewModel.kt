package com.cs4520.brainflex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel() : ViewModel() {

    init{

    }

    fun checkCredentials() {
       // make api calls to check if the credentials are correct
        // if there is a username, check password
        // if not, create a new one

    }

}