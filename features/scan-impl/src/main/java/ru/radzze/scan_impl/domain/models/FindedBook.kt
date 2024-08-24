package ru.radzze.scan_impl.domain.models

data class FindedBook(
    val image: String?,
    val title: String,
    val author: String,
    val isChecked: Boolean,
)
