package ru.radzze.scan_impl.domain.models

data class FindedBookApi(
    val id: String?,
    val name: String?,
    val author: String?,
    val description: String?,
    val sourceName: String?,
    val image: String?,
    val genre: String?,
    val numberOfPages: String?,
    val isbn: String?,
    val parsingDate: String?,
    val publisherYear: String?,
    val siteBookId: String?,
    val breadcrqmbs: String?,
    val sourceUrl: String?,
    val age: String?,
    val publishingHouse: String?,
    val rating: String?,
    val isChecked:Boolean
)
