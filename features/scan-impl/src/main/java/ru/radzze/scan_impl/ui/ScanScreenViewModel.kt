package ru.radzze.scan_impl.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.scan_impl.domain.ScanService
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val scanService: ScanService
) : ViewModel() {

    private val _scanRequest = mutableStateOf(NETWORK_STATUS.NONE)
    val scanRequest by _scanRequest

    var isFlashModeOn by mutableStateOf(false)

    fun sendImageToScan(image: String) {
        _scanRequest.value = NETWORK_STATUS.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            _scanRequest.value = if (scanService.postScanImage(image)
                    .code() == 200
            ) NETWORK_STATUS.SUCCESS else NETWORK_STATUS.FAILED
        }
    }

    fun changeFlashModeState(){
        isFlashModeOn = !isFlashModeOn
    }

    fun cleanScanRequest(){
        _scanRequest.value = NETWORK_STATUS.NONE
    }

}