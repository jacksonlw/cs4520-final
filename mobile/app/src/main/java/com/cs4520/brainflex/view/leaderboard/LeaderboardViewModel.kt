package com.cs4520.brainflex.view.leaderboard

import androidx.lifecycle.ViewModel
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository

class LeaderboardViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {

}