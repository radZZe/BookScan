package ru.radzze.scan_impl.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.scan_impl.domain.BookInfoService
import ru.radzze.scan_impl.domain.models.CheckBox
import ru.radzze.scan_impl.domain.models.ScannedBook
import javax.inject.Inject

@HiltViewModel
class AddBookScreenViewModel @Inject constructor(
    private val bookInfoService: BookInfoService
) : ViewModel() {
    private val _genresRequest = mutableStateOf(NETWORK_STATUS.NONE)
    val genresRequest by _genresRequest

    var title by mutableStateOf("")
    var author by mutableStateOf("")
    var ISBN by mutableStateOf("")
    var publisher by mutableStateOf("")
    var pages by  mutableStateOf("")
    var ageLimit by mutableStateOf("")
    var description by mutableStateOf("")
    var format by mutableStateOf("")
    var language by mutableStateOf("")
    var coverType by mutableStateOf("")
    var year by mutableStateOf("")
    val genre =  mutableStateListOf<String>()

    fun onTitleChanged(text:String){
        title = text;
    }

    fun onYearChanged(text:String){
        year = text
    }

    fun onAuthorChanged(text:String){
        author = text;
    }
    fun onISBNChanged(text:String){
        ISBN = text;
    }
    fun onPublisherChanged(text:String){
        publisher = text;
    }
    fun onPagesChanged(text:String){
        pages = text;
    }
    fun onAgeLimitChanged(text:String){
        ageLimit = text;
    }
    fun onDescriptionChanged(text:String){
        description = text;
    }

    var genres = mutableStateListOf<CheckBox>()
    val languageList = mutableStateListOf<CheckBox>(
        CheckBox("Русский", false),
        CheckBox("Английский", false),
    )

    val formatList = mutableStateListOf<CheckBox>(
        CheckBox("Печатная книга", false),
        CheckBox("Журнал", false),
    )

    val coverTypeList = mutableStateListOf<CheckBox>(
        CheckBox("Мягкий", false),
        CheckBox("Твердый", false),
        CheckBox("Обрезной", false),
        CheckBox("С кантом", false),
        CheckBox("Интегральный", false),
    )

    var showBottomSheetLanguage by mutableStateOf(false)
    var showBottomSheetFormat by mutableStateOf(false)
    var showBottomSheetCoverType by mutableStateOf(false)
    var showBottomSheetGenres by mutableStateOf(false)
    var showBottomSheetYear by mutableStateOf(false)

    fun changeLanguageState(data: SnapshotStateList<CheckBox>, index: Int) {
        data.forEachIndexed { position, _ ->
            val state = data[index].state
            if (index == position) {
                if(state) language = "" else language = data[position].label
                data[position] = data[position].copy(state = !state)
            } else {
                data[position] = data[position].copy(state = false)
            }

        }
    }

    fun changeCoverTypeState(data: SnapshotStateList<CheckBox>, index: Int) {
        data.forEachIndexed { position, _ ->
            val state = data[index].state
            if (index == position) {
                if(state) coverType = "" else coverType = data[position].label
                data[position] = data[position].copy(state = !state)
            } else {
                data[position] = data[position].copy(state = false)
            }

        }
    }




    fun changeFormatState(data: SnapshotStateList<CheckBox>, index: Int) {
        data.forEachIndexed { position, _ ->
            val state = data[index].state
            if (index == position) {
                if(state) format = "" else format = data[position].label
                data[position] = data[position].copy(state = !state)
            } else {
                data[position] = data[position].copy(state = false)
            }

        }
    }

    fun getGenresString():String{
        var result = ""
        genre.forEach {
            result += "$it, "
        }
        return result
    }

    fun changeGenresState(data: SnapshotStateList<CheckBox>, index: Int) {
        data.forEachIndexed { position, _ ->
            val state = data[index].state
            if (index == position) {
                if(state) genre.remove(data[position].label) else genre.add(data[position].label)
                data[position] = data[position].copy(state = !state)
            }
        }

    }



    fun onChangeLanguageBottomSheetState() {
        showBottomSheetLanguage = !showBottomSheetLanguage
    }

    fun onChangeYearBottomSheetState() {
        showBottomSheetYear = !showBottomSheetYear
    }

    fun onChangeFormatBottomSheetState() {
        showBottomSheetFormat = !showBottomSheetFormat
    }

    fun onChangeCoverTypeBottomSheetState() {
        showBottomSheetCoverType = !showBottomSheetCoverType
    }

    fun onChangeGenresBottomSheetState() {
        showBottomSheetGenres = !showBottomSheetGenres
    }


    fun getGenres() {
        _genresRequest.value = NETWORK_STATUS.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            val response = bookInfoService.getBookGenres().execute()
            if (response.code() == 200
            ) {
                _genresRequest.value = NETWORK_STATUS.SUCCESS
                if (response.body() != null) {
                    genres.clear()
                    genres.addAll(response.body()!!.bookGenres.map {
                        CheckBox(it, false)
                    }.toList())
                }
            } else _genresRequest.value = NETWORK_STATUS.FAILED
        }
    }

}