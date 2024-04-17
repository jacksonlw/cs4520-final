package com.cs4520.brainflex.dto

import com.google.gson.annotations.SerializedName

data class LeaderboardData (
    @SerializedName("scores") val scores: List<Score>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("total") val total: Int
)
