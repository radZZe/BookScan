package ru.radzze.library_impl.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.radzze.library_impl.data.FilterRequest
import ru.radzze.library_impl.domain.LibraryService
import javax.inject.Inject


@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val service: LibraryService
) : ViewModel() {

    private val _filterRequest = mutableStateOf<FilterRequest?>(null)
    val filterRequest = _filterRequest

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
    val lastRequests = listOf("Jopa", "Jopa", "Jopa", "Jopa", "Jopa", "Jopa", "Jopa")

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

}

data class Book(
    val name: String,
    val author: String,
    val isbn: String,
    val image: String
)