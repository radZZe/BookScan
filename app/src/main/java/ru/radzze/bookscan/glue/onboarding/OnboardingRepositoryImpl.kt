package ru.radzze.bookscan.glue.onboarding

import kotlinx.coroutines.flow.Flow
import ru.radzze.data.onboarding.OnboardingDataRepository
import ru.radzze.onboarding_impl.domain.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
private val onboardingDataRepository: OnboardingDataRepository
):OnboardingRepository {
    override suspend fun saveOnboardingState() {
        onboardingDataRepository.saveOnboardingState()
    }

    override suspend fun getOnboardingState(): Boolean {
       return  onboardingDataRepository.getOnboardingState()
    }
}