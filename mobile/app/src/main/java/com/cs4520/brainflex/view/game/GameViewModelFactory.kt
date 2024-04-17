package com.cs4520.brainflex.view.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository

class GameViewModelFactory(
    private val apiClient: ApiClient,
    private val userRepo: UserRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(this.apiClient, this.userRepo) as T
        }
        throw IllegalArgumentException("viewModel must be a GameViewModel")
    }
}