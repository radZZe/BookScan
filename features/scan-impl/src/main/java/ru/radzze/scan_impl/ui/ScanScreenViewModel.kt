package ru.radzze.scan_impl.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.radzze.scan_impl.domain.ScanService
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val scanService: ScanService
) : ViewModel() {

    var isFlashModeOn by mutableStateOf(false)

    fun changeFlashModeState(){
        isFlashModeOn = !isFlashModeOn
    }

}