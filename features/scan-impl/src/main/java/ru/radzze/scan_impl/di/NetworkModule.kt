package ru.radzze.scan_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.radzze.scan_impl.domain.ScanService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        return OkHttpClient().newBuilder()
//            .addInterceptor(interceptor)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideScanService(
//        client: OkHttpClient
    ): ScanService {
        return Retrofit
            .Builder()
            .baseUrl("https://lj9wj.wiremockapi.cloud/")
//            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScanService::class.java)
    }
}