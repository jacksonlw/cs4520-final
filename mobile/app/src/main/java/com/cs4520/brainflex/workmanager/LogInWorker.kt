package com.cs4520.brainflex.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.brainflex.dao.AppDatabase
import com.cs4520.brainflex.dao.UserRepository
import java.util.Calendar


class LogInWorker(private val context: Context, val params: WorkerParameters) :  CoroutineWorker(context, params) {
    override suspend fun doWork(): Result  {
        return try {
            val currentTime = Calendar.getInstance().time
            Log.d("doWork", "Timestamp: $currentTime")
            val dao = AppDatabase.get(context = context).userDao()
            val repo = UserRepository(dao)
            try {
                val users = repo.recent
                return if (users.value?.isEmpty() == true) {
                    Result.success()
                } else {
                    users.value?.forEach{ item ->
                        dao.add(item)
                    }
                    Result.success()
                }
            } catch (e: Exception) {
                Log.e("API error", e.message!!)
                Result.failure()
            }
        } catch (e: Throwable) {
            Log.e("Login Work failed", e.message!!)
            Result.failure()
        }
    }
}