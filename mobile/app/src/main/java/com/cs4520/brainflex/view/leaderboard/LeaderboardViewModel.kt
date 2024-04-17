package com.cs4520.brainflex.view.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.dto.Score
import com.cs4520.brainflex.workmanager.LeaderboardWorkManager
import com.cs4520.brainflex.workmanager.LogInWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class LeaderboardViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {
    private var limit = 20
    private var offset = 0
    private var total: Int? = null

    private val _scores = MutableLiveData<List<Score>>(listOf())
    val scores: LiveData<List<Score>> = _scores

    private val wm: WorkManager = LeaderboardWorkManager.worker
    fun loadNextPage() {
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
                schedulePeriodicWork(wm)
                offset += limit
                total = data.total
            }
        }
    }

    private fun schedulePeriodicWork(wm: WorkManager) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<LogInWorker>(
            repeatInterval = 1, // Repeat every hour
            repeatIntervalTimeUnit = TimeUnit.HOURS,
        ).setInitialDelay(1, TimeUnit.HOURS).setConstraints(constraints).build()

        runCatching {
            wm.enqueueUniquePeriodicWork(
                "loginWorkManager",
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                periodicWorkRequest
            ).getResult()
        }.onSuccess {
            Log.d("LogInWorkManager", "Periodic work enqueued successfully.")
//            doSomethingWithLifecycleOwner(wm)
        }.onFailure { error ->
            Log.e("LogInWorkManager", "Error enqueuing periodic work: $error")
//            doSomethingWithLifecycleOwner(wm)
        }
    }
}