package ru.radzze.scan_impl.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.scan_impl.domain.ScanService
import ru.radzze.scan_impl.domain.models.FindBookRequestDto
import ru.radzze.scan_impl.domain.models.ImageDto
import javax.inject.Inject

@HiltViewModel
class FindBookViewModel @Inject constructor(

) : ViewModel() {
    val name = mutableStateOf("")
    val isbn = mutableStateOf("")

    fun onNameChanged(newText: String) {
        name.value = newText
    }

    fun onIsbnChanged(newIsbn: String) {
        isbn.value = newIsbn
    }


}