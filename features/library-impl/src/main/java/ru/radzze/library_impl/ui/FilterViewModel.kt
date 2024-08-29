package ru.radzze.library_impl.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.radzze.library_impl.data.CheckBox
import ru.radzze.library_impl.data.FilterRequest
import ru.radzze.library_impl.data.GridItem
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    private val _required = mutableStateOf("")
    val required = _required
    private val _pickedGenres = mutableStateListOf<String>()
    private val _author = mutableStateOf("")
    val author = _author
    private val _distributor = mutableStateOf("")
    val distributor = _distributor
    private val _showMoreAuthors = mutableStateOf(false)
    val showMoreAuthors = _showMoreAuthors
    private val _showMoreDistributors = mutableStateOf(false)
    val showMoreDistributors = _showMoreDistributors
    private val _year = mutableStateOf("")
    var showGenres by mutableStateOf(false)

    var genresCheckbox = mutableStateListOf<CheckBox>(CheckBox("Genre1", false), CheckBox("Genre1", false), CheckBox("Genre1", false), CheckBox("Genre2", false), CheckBox("Genre3", false), CheckBox("Genre4", false), CheckBox("Genre5", false))

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

    val genres = listOf("genre1", "genre2", "genre3", "genre4", "genre5", "genre6", "genre7", "genre8")
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
        showGenres = !showGenres
    }

    fun formFilter(): FilterRequest {
        return FilterRequest(
            scanFromNew = radioOptions["От нового к старому"]!!.value,
            genres = _pickedGenres,
            authors = authors.filter { it.state.value }.map { it.title },
            year = _year.value,
            distributors = distributors.filter { it.state.value }.map { it.title }
        )
    }

    fun changeGenresState(data: SnapshotStateList<CheckBox>, index: Int) {
        data.forEachIndexed { position, _ ->
            val state = data[index].state
            if (index == position) {
                if (state) _pickedGenres.remove(data[position].label) else _pickedGenres.add(data[position].label)
                data[position] = data[position].copy(state = !state)
            }
        }
    }

    fun clearGenres(data: SnapshotStateList<CheckBox>) {
        _pickedGenres.clear()
        data.forEach {
            it.state = false
        }
    }

}