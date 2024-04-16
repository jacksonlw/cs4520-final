package com.cs4520.brainflex.dto

import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("score") val score: Int,
    @SerializedName("inserted_at") val insertedAt: String
)
