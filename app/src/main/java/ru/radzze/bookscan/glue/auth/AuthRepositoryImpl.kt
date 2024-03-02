package ru.radzze.bookscan.glue.auth

import ru.radzze.auth_impl.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRepository: AuthDataRepository
) : AuthRepository {

    override suspend fun saveAuthState() {
        authRepository.saveAuthState()
    }

    override suspend fun getAuthState(): Boolean {
        return authRepository.getAuthState()
    }
}