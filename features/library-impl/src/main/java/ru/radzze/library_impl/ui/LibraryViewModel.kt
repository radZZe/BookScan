package ru.radzze.library_impl.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.library_impl.data.FilterRequest
import ru.radzze.library_impl.domain.LibraryService
import ru.radzze.library_impl.domain.models.GetUserBooksDto
import javax.inject.Inject


@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val service: LibraryService
) : ViewModel() {

    private val _filterRequest = mutableStateOf<FilterRequest?>(null)
    val filterRequest = _filterRequest
    private lateinit var _rawLibraryBooks: List<GetUserBooksDto>
    private var _libraryBooks = mutableStateOf(listOf<GetUserBooksDto>())
    var tempLibraryBooks = mutableListOf<GetUserBooksDto>()
    val libraryBooks by _libraryBooks

    val books = listOf(
        Book(
            "Name",
            "Author",
            "ISBN: 978-5-93851-256-4",
            "https://edit.org/images/cat/book-covers-big-2019101610.jpg"
        ),
        Book(
            "Name",
            "Author",
            "ISBN: 978-5-93851-256-4",
            "https://edit.org/images/cat/book-covers-big-2019101610.jpg"
        ),
        Book(
            "Name",
            "Author",
            "ISBN: 978-5-93851-256-4",
            "https://edit.org/images/cat/book-covers-big-2019101610.jpg"
        ),
        Book(
            "Name",
            "Author",
            "ISBN: 978-5-93851-256-4",
            "https://edit.org/images/cat/book-covers-big-2019101610.jpg"
        ),
        Book(
            "Name",
            "Author",
            "ISBN: 978-5-93851-256-4",
            "https://edit.org/images/cat/book-covers-big-2019101610.jpg"
        )
    )
    val lastRequests = listOf("48 законов власти")

    private val _request = mutableStateOf("")
    val request = _request
    private val _gridMode = mutableStateOf(true)
    val gridMode = _gridMode
    private val _columnMode = mutableStateOf(false)
    val columnMode = _columnMode
    private val _isSearchClicked = mutableStateOf(false)
    val isSearchClicked = _isSearchClicked

    fun onRequestChanged(newText: String) {
        _request.value = newText
    }

    fun onGridActive() {
        _gridMode.value = true
        _columnMode.value = !_gridMode.value
    }

    fun onColumnActive() {
        _columnMode.value = true
        _gridMode.value = !_columnMode.value
    }

    fun onIsSearchClickedChange() {
        _isSearchClicked.value = !_isSearchClicked.value
    }

    fun initFilterRequest(filterRequest: FilterRequest?) {
        _filterRequest.value = filterRequest
    }

    fun setFalse() {
        _isSearchClicked.value = true
    }
    fun getUserBooks(){
        viewModelScope.launch(Dispatchers.IO) {
            val userId = service.getUserId().body()!!
            val response = service.getUserBooks(userId.userId)
            if (response.code() == 200
            ) {

                if(response.body()!=null){
                    _rawLibraryBooks = response.body()!!
                    for (item in _rawLibraryBooks){
                        tempLibraryBooks.add(item)
                    }
                    _libraryBooks.value = tempLibraryBooks
                }
            }
        }
    }

    fun clearFindedBook(){
        tempLibraryBooks.clear()
        _libraryBooks.value = mutableListOf<GetUserBooksDto>()
    }
}

data class Book(
    val name: String,
    val author: String,
    val isbn: String,
    val image: String
)