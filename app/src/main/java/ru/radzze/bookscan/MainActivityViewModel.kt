package ru.radzze.bookscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.onboarding_impl.domain.OnboardingRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {
    suspend fun getOnboardingState():Boolean{
        return onboardingRepository.getOnboardingState()
    }

}