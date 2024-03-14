package ru.radzze.bookscan.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.radzze.bookscan.MainActivity
import ru.radzze.auth_impl.domain.AuthRepository
import ru.radzze.bookscan.glue.auth.AuthDataRepository
import ru.radzze.bookscan.glue.auth.AuthRepositoryImpl
import ru.radzze.bookscan.glue.onboarding.OnboardingRepositoryImpl
import ru.radzze.data.auth.AuthDataRepositoryImpl
import ru.radzze.data.onboarding.OnboardingDataRepository
import ru.radzze.data.onboarding.OnboardingDataRepositoryImpl
import ru.radzze.onboarding_impl.domain.OnboardingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindOnboardingDataRepository(onboardingDataRepository: OnboardingDataRepositoryImpl): OnboardingDataRepository

    @Binds
    abstract fun bindOnboardingRepository(onboardingRepository: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    abstract fun bindAuthDataRepository(authDataRepository: AuthDataRepositoryImpl): AuthDataRepository

    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}