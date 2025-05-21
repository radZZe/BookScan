package ru.radzze.scan_impl.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
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
import ru.radzze.scan_impl.domain.models.GetBookDto
import ru.radzze.scan_impl.domain.models.ImageDto
import ru.radzze.scan_impl.domain.models.RawScannedResult
import ru.radzze.scan_impl.domain.models.ScannedBook
import ru.radzze.scan_impl.domain.models.UserBookDto
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ResultScanScreenViewModel @Inject constructor(
    private val scanService: ScanService
) : ViewModel() {

    private val _scanRequest = mutableStateOf(NETWORK_STATUS.NONE)
    val scanRequest by _scanRequest
    private val _scanRequestGet = mutableStateOf(NETWORK_STATUS.NONE)
    val scanRequestGet by _scanRequest
    private lateinit var _rawScannedBooks : RawScannedResult
    private lateinit var _rawScannedBooksGet : GetBookDto

    private val _scannedBooks = mutableStateOf(listOf<ScannedBook>())
    val scannedBook by _scannedBooks
    var tempScannedBooks = mutableListOf<ScannedBook>()

    fun getImageBase64FromUri(context: Context, imageUri: Uri): String {
        try {
            // Открываем InputStream для изображения по URI
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            // Преобразуем InputStream в Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Преобразуем Bitmap в ByteArray
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // Преобразуем ByteArray в строку Base64
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }


    fun splitData(id:String,info: String?): ScannedBook {
        if (info == null) {
            return ScannedBook("", "", "","")
        }
        
        val parts = info.split(" | ")
        if (parts.size != 9) {
            return ScannedBook("", "", "","")
        }

        val name = parts[0]
        val author = parts[1]
        val age = parts[2].toIntOrNull() ?: null
        val rating = parts[3].toIntOrNull() ?: null
        val description = parts[4]
        val genre = parts[5]
        val image = parts[6]
        val numberOfPages = parts[7].toIntOrNull() ?: null
//        throw IllegalArgumentException("Не удалось преобразовать количество страниц.")
        val publishingHouse = parts[8]


        return ScannedBook(id,name, author, genre)
    }
    fun sendImageToScan(image: String) {
        _scanRequest.value = NETWORK_STATUS.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            val response = scanService.postScanImage(ImageDto(image))
            if (response.code() == 200
            ) {
                _scanRequest.value = NETWORK_STATUS.SUCCESS

                if(response.body()!=null){
                    _rawScannedBooks = response.body()!!
                    for (item in _rawScannedBooks.result){
                        for (book in item.possibleBooks){
                            val responseBook = scanService.getFindedBook(book)
                            if (responseBook.code() == 200) {
                                _scanRequestGet.value = NETWORK_STATUS.SUCCESS
                                if (response.body() != null) {
                                    _rawScannedBooksGet = responseBook.body()!!
                                    val bookInfo = _rawScannedBooksGet.info
                                    val id = _rawScannedBooksGet.id
                                    if (bookInfo != null) {
                                        tempScannedBooks.add(splitData(id,bookInfo))

                                    }
                                }
                            }
                        }

                    }
                    _scannedBooks.value = tempScannedBooks
                }
            } else _scanRequest.value = NETWORK_STATUS.FAILED
        }
    }

    fun cleanScanRequest(){
        _scanRequest.value = NETWORK_STATUS.NONE
    }

    fun saveUserBooks(){
        scannedBook
        viewModelScope.launch(Dispatchers.IO) {
            val userId = scanService.getUserId().body()!!
            for (item in scannedBook){
                scanService.saveUserBook(UserBookDto(userId.userId,item.id))
            }
        }
    }
}

