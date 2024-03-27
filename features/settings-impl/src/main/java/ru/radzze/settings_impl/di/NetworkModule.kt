package ru.radzze.settings_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.radzze.settings_impl.domain.UserService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    @Provides
//    @Singleton
//    fun provideOkHttpClientUser(): OkHttpClient {
//        return OkHttpClient().newBuilder()
//            .build()
//    }

    @Provides
    @Singleton
    fun provideUserService(
//        client: OkHttpClient
    ): UserService {
        val url = "https://wyeok.wiremockapi.cloud/"
        return Retrofit.Builder()
            .baseUrl(url)
//            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}