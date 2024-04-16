package com.cs4520.brainflex.api.requests

import com.google.gson.annotations.SerializedName

data class LoginRequestBody (
    @SerializedName("username") val username: String
)
