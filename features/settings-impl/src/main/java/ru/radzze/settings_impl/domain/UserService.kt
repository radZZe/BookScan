package ru.radzze.settings_impl.domain

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.radzze.settings_impl.data.UserData

interface UserService {
    @GET("/api/user")
    suspend fun getUserData(): Response<UserData>
    @POST("/api/user")
    suspend fun updateUserData(@Body userData: UserData): Response<Unit>

}