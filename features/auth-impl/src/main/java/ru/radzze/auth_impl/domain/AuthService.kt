package ru.radzze.auth_impl.domain

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.radzze.auth_impl.data.AuthResponse
import ru.radzze.auth_impl.data.LoginRequest
import ru.radzze.auth_impl.data.SendCodeDto
import ru.radzze.auth_impl.data.VerifyDto


interface AuthService {
    @POST("/api/Auth/send-code")
    suspend fun authUser(@Body request: SendCodeDto): Response<Unit>

    @POST("/api/Auth/verify-code")
    suspend fun verifyUser(@Body loginRequest: LoginRequest): Response<VerifyDto>

    @GET("api/auth/fast/logout")
    suspend fun logOutUser(): Response<Unit>
}