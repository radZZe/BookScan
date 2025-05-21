package ru.radzze.library_impl.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.radzze.library_impl.domain.models.GetUserBooksDto
import ru.radzze.library_impl.domain.models.GetUserIdDto

interface LibraryService {
    @GET("api/User/books")
    suspend fun getUserBooks(@Query("userId") id:String):Response<List<GetUserBooksDto>>

    @GET("userId")
    suspend fun getUserId():Response<GetUserIdDto>
}