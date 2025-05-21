package ru.radzze.scan_impl.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.radzze.scan_impl.domain.BookInfoService
import ru.radzze.scan_impl.domain.ScanService
import java.net.InetAddress
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideScanService(okHttpClient: OkHttpClient): ScanService {
        return Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:5078/") // Используем HTTP
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScanService::class.java)
    }
    @Provides
    @Singleton
    fun provideBookInfoService(
    ): BookInfoService {
        return Retrofit
            .Builder()
            .baseUrl("https://lj9wj.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookInfoService::class.java)
    }
}