package ru.radzze.scan_impl.domain.models

data class AddedBook(
    val title: String,
    val author: String,
    val ISBN: String,
    val publisher: String,
    val pages: String,
    val ageLimit: String,
    val description: String,
    val format: String,
    val language: String,
    val coverType: String,
    val year: String,
    val genre: List<String>
)
