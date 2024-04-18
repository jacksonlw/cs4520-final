package com.cs4520.brainflex.api

import com.cs4520.brainflex.api.requests.LoginRequestBody
import com.cs4520.brainflex.api.requests.ScoreRequestBody
import com.cs4520.brainflex.dto.LeaderboardData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<LeaderboardData>

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body body: LoginRequestBody): Response<Unit>


    @Headers("Content-Type: application/json")
    @POST("leaderboard")
    suspend fun addScoreToLeaderboard(@Body body: ScoreRequestBody): Response<Unit>
}




