package com.cs4520.brainflex.view.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.dto.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeaderboardViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {
    private var limit = 20
    private var offset = 0
    private var total: Int? = null

    private val _scores = MutableLiveData<List<Score>>(listOf())
    val scores: LiveData<List<Score>> = _scores

    fun loadNextPage() {
        // Stop if next request will be over total
        if(total != null && offset - limit > total!!) {
            return
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val res = apiClient.getLeaderboard(limit, offset)
                if (!res.isSuccessful) {
                    // show error loading leaderboard
                    return@withContext
                }
                val data = res.body()
                if (data == null) {
                    // error loading scores
                    return@withContext
                }

                val newScores = data.scores
                _scores.postValue(_scores.value.orEmpty() + newScores)

                offset += limit
                total = data.total

            }
        }
    }
}