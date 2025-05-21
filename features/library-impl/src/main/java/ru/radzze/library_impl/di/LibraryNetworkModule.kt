package ru.radzze.library_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.radzze.library_impl.domain.LibraryService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LibraryNetworkModule() {


    @Provides
    @Singleton
    fun provideUserService(
        client: OkHttpClient
    ): LibraryService {
        val url = "http://10.0.2.2:5078/"
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryService::class.java)
    }

}