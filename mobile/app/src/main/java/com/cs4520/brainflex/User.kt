package com.cs4520.brainflex

data class User(
    var username: String = "",
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty()
    }
}