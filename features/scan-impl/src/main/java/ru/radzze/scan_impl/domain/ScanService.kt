package ru.radzze.scan_impl.domain



import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Query
import ru.radzze.scan_impl.domain.models.FindBookRequestDto
import ru.radzze.scan_impl.domain.models.FindBookResponseDto
import ru.radzze.scan_impl.domain.models.FindedBook
import ru.radzze.scan_impl.domain.models.GetBookDto
import ru.radzze.scan_impl.domain.models.GetUserIdDto
import ru.radzze.scan_impl.domain.models.ImageDto
import ru.radzze.scan_impl.domain.models.RawScannedResult
import ru.radzze.scan_impl.domain.models.ScannedBook
import ru.radzze.scan_impl.domain.models.UserBookDto

interface ScanService {
    @POST("Recognition")
    suspend fun postScanImage(@Body image: ImageDto): Response<RawScannedResult>

    @GET("book")
    suspend fun getFindedBook(@Query("id") id:String):Response<GetBookDto>

    @GET("userId")
    suspend fun getUserId():Response<GetUserIdDto>

    @POST("api/User/books/add")
    suspend fun saveUserBook(@Body userBook:UserBookDto):Response<Unit>

    @GET("api/User/books/find")
    suspend fun findBook(@Body findBookRequestDto: FindBookRequestDto):Response<List<FindBookResponseDto>>
}