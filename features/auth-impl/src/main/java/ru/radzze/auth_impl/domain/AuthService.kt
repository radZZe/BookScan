package ru.radzze.auth_impl.domain

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.radzze.auth_impl.data.AuthResponse
import ru.radzze.auth_impl.data.LoginRequest


interface AuthService {
    @POST("api/auth/fast")
    suspend fun authUser(@Body email: String): Response<Any?>

    @POST("/auth/login")
    suspend fun verifyUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @GET("api/auth/fast/logout")
    suspend fun logOutUser(): Response<Any?>
}