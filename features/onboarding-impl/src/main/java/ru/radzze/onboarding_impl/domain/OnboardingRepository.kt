package ru.radzze.onboarding_impl.domain

import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun saveOnboardingState()
    suspend fun getOnboardingState(): Boolean
}