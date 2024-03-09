package ru.radzze.auth_impl.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.radzze.auth_impl.data.LoginRequest
import ru.radzze.auth_impl.domain.AuthRepository
import ru.radzze.auth_impl.domain.AuthService
import javax.inject.Inject


@HiltViewModel
class CodeVerificationViewModel @Inject constructor(
    private val service: AuthService,
    private val repository: AuthRepository
) : ViewModel() {

    private val _textList = mutableStateListOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        ),
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        ),
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        ),
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        ),
    )
    val textList: List<TextFieldValue> = _textList

    val requestList = listOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester()
    )

    private val _isOver = MutableStateFlow(false)
    val isOver = _isOver.asStateFlow()

    private val _isValid: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValid = _isValid.asStateFlow()

    private val _timer = MutableStateFlow(59)
    val timer = _timer.asStateFlow()

    private val _blockTimer = MutableStateFlow(300)
    val blockTimer = _blockTimer.asStateFlow()

    private val _isUnblocked: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isUnblocked = _isUnblocked.asStateFlow()

    private var failedAttempts by mutableIntStateOf(0)

    val email = mutableStateOf("")

    init {
        viewModelScope.launch {
            resendCodeTimer()
        }
    }

    private suspend fun resendCodeTimer() {
        while (true) {
            delay(1000)
            _timer.value--
            if (_timer.value == 0) _isOver.update { true }
        }
    }

    fun changeTextListItem(index: Int, newValue: TextFieldValue) {
        _textList[index] = newValue
    }

    fun resendCode() {
        _timer.value = 59
        _isOver.update { false }
        failedAttempts = 0
        clearFields()
    }

    private fun clearFields() {
        for (index in _textList.indices) {
            _textList[index] = TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        }
        _isValid.update { null }
    }

    private suspend fun unblockTimer() {
        while (true) {
            delay(1000)
            _blockTimer.value--
            if (_blockTimer.value == 0) _isUnblocked.update { true }
        }
    }

    fun nextFocus() {
        for (i in textList.indices) {
            if (textList[i].text == "") {
                if (i < textList.size) {
                    requestList[i].requestFocus()
                    break
                }
            }
        }
    }

    fun previousFocus() {
        for (i in textList.indices) {
            if (textList[i].text == "") {
                if (i < textList.size) {
                    val index = if (i == 0) 0 else i - 1
                    requestList[index].requestFocus()
                    break
                }
            }
        }
    }

    fun connectInputtedCode(
        onVerifyCode: ((success: Boolean) -> Unit)? = null
    ) {
        var code = ""
        for (text in _textList) {
            code += text.text
        }
        if (code.length == 4) {
            verifyCode(
                code,
                onSuccess = {
                    _isValid.update { true }
                    onVerifyCode?.let {
                        it(true)
                    }
                },
                onError = {
                    _isValid.update { false }
                    onVerifyCode?.let {
                        it(false)
                    }
                }
            )
        } else {
            _isValid.update { null }
        }
    }

    private fun verifyCode(
        code: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val loginRequest = LoginRequest(email = email.value, code = code.toInt())

                val result = service.verifyUser(loginRequest).isSuccessful
                if (result) {
                    onSuccess()
                } else {
                    failedAttempts++
                    if (failedAttempts == 3) {
                        _isUnblocked.update { false }
                        viewModelScope.launch { unblockTimer() }
                    }
                    onError()
                }

            }
        } catch (_: Exception) {

        }
    }

    fun saveAuthState() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveAuthState()
        }
    }

}