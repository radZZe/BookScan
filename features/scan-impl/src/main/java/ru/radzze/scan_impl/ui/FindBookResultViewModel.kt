package ru.radzze.scan_impl.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.radzze.scan_impl.domain.models.FindedBook
import javax.inject.Inject

@HiltViewModel
class FindBookResultViewModel @Inject constructor():ViewModel() {
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
}