package ru.radzze.bookscan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.auth_impl.AuthFeatureImpl
import ru.radzze.library_api.LibraryFeatureApi
import ru.radzze.library_impl.LibraryFeatureImpl
import ru.radzze.onboarding_api.OnboardingFeatureApi
import ru.radzze.onboarding_impl.OnboardingFeatureImpl
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.scan_impl.ScanFeatureImpl
import ru.radzze.settings_api.SettingsFeatureApi
import ru.radzze.settings_impl.SettingsFeatureImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindAuthFeatureApi(authFeatureImpl: AuthFeatureImpl):AuthFeatureApi

    @Binds
    abstract fun bindOnboardingFeatureApi(onboardingFeatureImpl: OnboardingFeatureImpl):OnboardingFeatureApi

    @Binds
    abstract fun bindScanFeatureApi(scanFeatureImpl: ScanFeatureImpl):ScanFeatureApi

    @Binds
    abstract fun bindSettingsFeatureApi(settingsFeatureImpl: SettingsFeatureImpl):SettingsFeatureApi

    @Binds
    abstract fun bindSLibraryFeatureApi(libraryFeatureImpl: LibraryFeatureImpl): LibraryFeatureApi

}