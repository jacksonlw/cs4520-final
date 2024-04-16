package com.cs4520.brainflex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserDao

class LogInViewModelFactory (
    private val apiClient: ApiClient,
    private val userDao: UserDao,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogInViewModel(this.apiClient, this.userDao) as T
        }
        throw IllegalArgumentException("viewModel must be a ProductListViewModel")
    }
}