package ru.radzze.auth_impl.data

data class LoginRequest(
    val email: String,
    val code: Int
)