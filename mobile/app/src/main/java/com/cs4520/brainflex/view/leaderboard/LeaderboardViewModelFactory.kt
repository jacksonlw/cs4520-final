package com.cs4520.brainflex.view.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository

class LeaderboardViewModelFactory (
    private val apiClient: ApiClient,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(this.apiClient) as T
        }
        throw IllegalArgumentException("viewModel must be a LeaderboardViewModel")
    }
}