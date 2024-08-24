package ru.radzze.core.models

data class Book(
    val image:String?,
    val title:String,
    val author:String,
    val genres:List<String>,
    val isbn:String,
    val publisher:String,
    val year:Int,
    val language:BookLanguage,
    val format:BookFormat,
    val coverType:BookCover,
    val pages:Int,
    val ageLimit:BookAgeLimit,
    val description:String,
)
