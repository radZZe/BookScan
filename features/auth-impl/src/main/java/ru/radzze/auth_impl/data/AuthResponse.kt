package ru.radzze.auth_impl.data

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
