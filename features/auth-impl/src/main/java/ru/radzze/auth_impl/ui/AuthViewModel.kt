package ru.radzze.auth_impl.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
) : ViewModel() {
    var enabledState by mutableStateOf(false)
    var email by mutableStateOf("")

    private fun isValidEmail() {
        enabledState = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onEmailChange(newText: String) {
        email = newText
        isValidEmail()
    }

}