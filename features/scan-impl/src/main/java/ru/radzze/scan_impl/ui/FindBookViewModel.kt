package ru.radzze.scan_impl.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindBookViewModel @Inject constructor() : ViewModel() {
    val name = mutableStateOf("")
    val isbn = mutableStateOf("")

    fun onNameChanged(newText: String) {
        name.value = newText
    }

    fun onIsbnChanged(newIsbn: String) {
        isbn.value = newIsbn
    }
}