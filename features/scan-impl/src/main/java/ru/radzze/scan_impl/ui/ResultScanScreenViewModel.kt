package ru.radzze.scan_impl.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.scan_impl.domain.ScanService
import ru.radzze.scan_impl.domain.models.ScannedBook
import javax.inject.Inject

@HiltViewModel
class ResultScanScreenViewModel @Inject constructor(
    private val scanService: ScanService
) : ViewModel() {

    private val _scanRequest = mutableStateOf(NETWORK_STATUS.NONE)
    val scanRequest by _scanRequest
    private val _scannedBooks = mutableStateOf(listOf<ScannedBook>())
    val scannedBook by _scannedBooks

    fun sendImageToScan(image: String) {
        _scanRequest.value = NETWORK_STATUS.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            if (scanService.postScanImage(image)
                    .code() == 200
            ) {
                _scanRequest.value = NETWORK_STATUS.SUCCESS
                val list = mutableListOf<ScannedBook>()
                for (i in 0..10){
                    list.add(ScannedBook(title = "title$i", author = "author$i", genre = "genre$i"))
                }
                _scannedBooks.value = list

            } else _scanRequest.value = NETWORK_STATUS.FAILED
        }
    }

    fun cleanScanRequest(){
        _scanRequest.value = NETWORK_STATUS.NONE
    }
}

