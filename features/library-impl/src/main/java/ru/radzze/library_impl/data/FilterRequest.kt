package ru.radzze.library_impl.data

data class FilterRequest(
    val scanFromNew: Boolean? = true,
    val genres: List<String>?,
    val authors: List<String>?,
    val year: String?,
    val distributors: List<String>?
)
