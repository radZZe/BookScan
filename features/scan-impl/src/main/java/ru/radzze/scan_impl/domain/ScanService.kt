package ru.radzze.scan_impl.domain



import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Query
import ru.radzze.scan_impl.domain.models.FindedBook
import ru.radzze.scan_impl.domain.models.ScannedBook

interface ScanService {
    @POST("scan")
    suspend fun postScanImage(@Body image:String): Response<List<ScannedBook>>

    @GET("find")
    suspend fun getFindedBook(@Query("name") id:String,@Query("name") isbn:String):Response<List<FindedBook>>
}