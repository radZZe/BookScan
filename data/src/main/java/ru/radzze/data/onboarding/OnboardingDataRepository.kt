package ru.radzze.data.onboarding

import kotlinx.coroutines.flow.Flow

interface OnboardingDataRepository {
    suspend fun saveOnboardingState()
    suspend fun getOnboardingState(): Boolean
}