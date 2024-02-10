package ru.radzze.bookscan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.auth_impl.AuthFeatureImpl
import ru.radzze.onboarding_api.OnboardingFeatureApi
import ru.radzze.onboarding_impl.OnboardingFeatureImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindAuthFeatureApi(authFeatureImpl: AuthFeatureImpl):AuthFeatureApi

    @Binds
    abstract fun bindOnboardingFeatureApi(onboardingFeatureImpl: OnboardingFeatureImpl):OnboardingFeatureApi

//    @Provides
//    fun provideAuthFeatureApi(authFeatureImpl: AuthFeatureImpl):AuthFeatureApi{
//        return authFeatureImpl
//    }
//
//    @Provides
//    fun provideAuthFeatureImpl():AuthFeatureImpl{
//        return AuthFeatureImpl()
//    }
//
//    @Provides
//    fun provideOnboardingFeatureApi(onboardingFeatureImpl: OnboardingFeatureImpl):OnboardingFeatureApi{
//        return onboardingFeatureImpl
//    }
//
//    @Provides
//    fun provideOnboardingFeatureImpl(authFeatureApi:AuthFeatureApi):OnboardingFeatureImpl{
//        return OnboardingFeatureImpl(authFeatureApi)
//    }
}