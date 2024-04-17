package com.cs4520.brainflex.dto

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String,
    @SerializedName("inserted_at") val insertedAt: String
)
