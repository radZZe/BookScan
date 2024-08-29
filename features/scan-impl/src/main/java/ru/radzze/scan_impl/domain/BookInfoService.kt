package ru.radzze.scan_impl.domain

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.radzze.scan_impl.domain.models.BookGenres

interface BookInfoService {
    @GET("genres")
    fun getBookGenres(): Call<BookGenres>
}