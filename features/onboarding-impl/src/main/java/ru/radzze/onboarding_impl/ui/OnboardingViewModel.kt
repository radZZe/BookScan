package ru.radzze.onboarding_impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.onboarding_impl.domain.OnboardingRepository
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepository
):ViewModel() {

    fun saveOnboardingState(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOnboardingState()
        }
    }
}