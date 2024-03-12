package ru.radzze.auth_impl.ui

import android.util.Patterns
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.radzze.auth_impl.domain.AuthService
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val service: AuthService
) : ViewModel() {

    var enabledState by mutableStateOf(false)
    var email by mutableStateOf("")
    var errorState by mutableStateOf(false)

    private fun isValidEmail() {
        enabledState = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun onErrorStateChange() {
        errorState = !errorState
    }

    fun onEmailChange(newText: String) {
        email = newText
        isValidEmail()
        if (!enabledState) errorState = false
    }

    fun sendCodeToEmail(onNavigate: (String) -> Unit) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val result = service.authUser(email).isSuccessful
                if (result) {
                    withContext(Dispatchers.Main) { onNavigate(email) }
                } else {
                    onErrorStateChange()
                }
            }
        } catch (_: Exception) {

        }
    }

}