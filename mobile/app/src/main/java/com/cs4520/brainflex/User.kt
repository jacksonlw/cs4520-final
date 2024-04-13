package com.cs4520.brainflex

data class User(
    var username: String = "",
    var pwd: String = "",
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && pwd.isNotEmpty()
    }
}