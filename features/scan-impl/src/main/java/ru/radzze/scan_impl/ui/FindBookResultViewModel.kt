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
import ru.radzze.scan_impl.domain.models.FindBookRequestDto
import ru.radzze.scan_impl.domain.models.FindBookResponseDto
import ru.radzze.scan_impl.domain.models.FindedBook
import ru.radzze.scan_impl.domain.models.FindedBookApi
import ru.radzze.scan_impl.domain.models.RawScannedResult
import ru.radzze.scan_impl.domain.models.ScannedBook
import javax.inject.Inject

@HiltViewModel
class FindBookResultViewModel @Inject constructor(
    private val scanService: ScanService
):ViewModel() {
    private lateinit var _rawFindedBooks : List<FindBookResponseDto>
    private val _findedBooks = mutableStateOf(listOf<FindedBookApi>())
    val findedBook by _findedBooks
    var tempFindedBooks = mutableListOf<FindedBookApi>()

    val dataList = mutableStateListOf<FindedBook>(
        FindedBook(null,"Это Владивосток, детка","Лютература",false),
        FindedBook(null,"Это Владивосток, детка","Лютература",false),
        FindedBook(null,"Это Владивосток, детка","Лютература",false),
        FindedBook(null,"Это Владивосток, детка","Лютература",false)
    )

    fun changeItemState(index:Int){
        dataList.forEachIndexed { position,_->
            val state = dataList[index].isChecked
            if(index==position){
                dataList[position] = dataList[position].copy(isChecked = !state)
            }else{
                dataList[position] = dataList[position].copy(isChecked = false)
            }
        }
    }

    fun findBook(title:String,isbn:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = scanService.findBook(FindBookRequestDto(title, isbn))
            if (response.code() == 200
            ) {
                if(response.body()!=null){
                    _rawFindedBooks = response.body()!!
                    for (item in _rawFindedBooks){
                        tempFindedBooks.add(toFndedBook(item))
                    }
                    _findedBooks.value = tempFindedBooks
                }
            }
        }
    }

    fun toFndedBook(item: FindBookResponseDto):FindedBookApi{
        var findedBook = FindedBookApi(
            id = item.id,
            name = item.name,
            author = item.author,
            description = item.description,
            sourceName = item.sourceName,
            image = item.image,
            genre = item.genre,
            numberOfPages = item.numberOfPages,
            isbn = item.isbn,
            parsingDate = item.parsingDate,
            publisherYear = item.publisherYear,
            siteBookId = item.siteBookId,
            breadcrqmbs = item.breadcrqmbs,
            sourceUrl = item.sourceUrl,
            age = item.age,
            publishingHouse = item.publishingHouse,
            rating = item.rating,
            isChecked = false
        )
        return findedBook
    }
}