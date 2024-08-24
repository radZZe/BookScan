package ru.radzze.library_impl.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.radzze.library_impl.data.FilterRequest
import ru.radzze.library_impl.data.GridItem
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    private val _required = mutableStateOf("")
    val required = _required
    private val _author = mutableStateOf("")
    val author = _author
    private val _distributor = mutableStateOf("")
    val distributor = _distributor
    private val _showMoreAuthors = mutableStateOf(false)
    val showMoreAuthors = _showMoreAuthors
    private val _showMoreDistributors = mutableStateOf(false)
    val showMoreDistributors = _showMoreDistributors
    private val _year = mutableStateOf("")
    private val _showGenres = mutableStateOf(false)
    val showGenres = _showGenres

    val radioOptions = mapOf<String, MutableState<Boolean>> (
        "От нового к старому" to mutableStateOf(true),
        "От старого к новому" to mutableStateOf(false)
    )

    val authors = listOf(
        GridItem("author", mutableStateOf(false)),
        GridItem("authorauthorauthor", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("authorauthor", mutableStateOf(false)),
        GridItem("auth", mutableStateOf(false)),
        GridItem("or", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("authorauthorauthor", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("authorauthor", mutableStateOf(false)),
        GridItem("auth", mutableStateOf(false)),
        GridItem("or", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
        GridItem("author", mutableStateOf(false)),
    )

    val distributors = listOf(
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
        GridItem("distributor", mutableStateOf(false)),
    )

    val genres = listOf("genre", "genre", "genre", "genre", "genre", "genre", "genre", "genre")
    fun onRadioSwitch(label: String) {
        radioOptions.forEach { pair ->
            pair.value.value = label == pair.key
        }
    }

    fun onRequiredChanged(newText: String) {
        _required.value = newText
    }

    fun onAuthorChanged(newText: String) {
        _author.value = newText
    }

    fun onDistributorChanged(newText: String) {
        _distributor.value = newText
    }

    fun onGridItemChanged(mode: String, index: Int) {
        if (mode == "Автор") authors[index].state.value = !authors[index].state.value
        else distributors[index].state.value = !distributors[index].state.value
    }

    fun showMoreAuthors() {
        _showMoreAuthors.value = !_showMoreAuthors.value
    }

    fun showMoreDistributors() {
        _showMoreDistributors.value = !_showMoreDistributors.value
    }

    fun onYearChanged(newText: String) {
        _year.value = newText
    }

    fun onShowGenres() {
        _showGenres.value = !_showGenres.value
    }

    fun formFilter(): FilterRequest {
        return FilterRequest(
            scanFromNew = radioOptions["От нового к старому"]!!.value,
            genres = genres,
            authors = authors.filter { it.state.value }.map { it.title },
            year = _year.value,
            distributors = distributors.filter { it.state.value }.map { it.title }
        )
    }

}