package com.cs4520.brainflex.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.workmanager.LogInWorkManager

class LogInViewModelFactory(
    private val apiClient: ApiClient,
    private val userRepo: UserRepository,
    private val loginWm: LogInWorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogInViewModel(this.apiClient, this.userRepo, this.loginWm) as T
        }
        throw IllegalArgumentException("viewModel must be a LogInViewModel")
    }
}