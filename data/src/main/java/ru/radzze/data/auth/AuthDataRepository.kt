package ru.radzze.bookscan.glue.auth

interface AuthDataRepository {

    suspend fun saveAuthState()

    suspend fun getAuthState(): Boolean

}