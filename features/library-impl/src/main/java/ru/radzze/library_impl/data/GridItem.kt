package ru.radzze.library_impl.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class GridItem(
    val title: String,
    val state: MutableState<Boolean>
)