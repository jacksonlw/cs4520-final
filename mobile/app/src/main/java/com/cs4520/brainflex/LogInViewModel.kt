package com.cs4520.brainflex

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.api.requests.LoginRequestBody
import com.cs4520.brainflex.dao.UserDao
import com.cs4520.brainflex.dao.UserEntity
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.dto.User
import com.cs4520.brainflex.workmanager.LeaderboardWorkManager
import com.cs4520.brainflex.workmanager.LogInWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class LogInViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {

    val recentUsernames = Transformations.map(userRepo.recent) { entities ->
        entities.map { it.username }
    }

    private val _loginResponseEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val loginResponseEvent = _loginResponseEvent.asSharedFlow()
    private val wm: WorkManager = LeaderboardWorkManager.worker

    fun login(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val body = LoginRequestBody(username)
                val res = apiClient.login(body)
                if (!res.isSuccessful) {
                    _loginResponseEvent.tryEmit(false)
                    return@withContext
                }

                _loginResponseEvent.tryEmit(true)
                userRepo.add(UserEntity(username))
                schedulePeriodicWork(wm)
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
        }.onFailure { error ->
            Log.e("LogInWorkManager", "Error enqueuing periodic work: $error")
        }
    }

}