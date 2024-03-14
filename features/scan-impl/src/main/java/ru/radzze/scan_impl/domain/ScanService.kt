package ru.radzze.scan_impl.domain



import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface ScanService {
    @POST("scan")
    suspend fun postScanImage(@Body image:String): Response<ResponseBody>
}