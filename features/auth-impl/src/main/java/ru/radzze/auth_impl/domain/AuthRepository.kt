package ru.radzze.auth_impl.domain

interface AuthRepository {

    suspend fun saveAuthState()

    suspend fun getAuthState() : Boolean
}