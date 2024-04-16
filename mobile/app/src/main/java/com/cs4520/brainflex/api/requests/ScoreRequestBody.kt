package com.cs4520.brainflex.api.requests

import com.google.gson.annotations.SerializedName

data class ScoreRequestBody (
    @SerializedName("username") val username: String,
    @SerializedName("score") val score: Int
)
