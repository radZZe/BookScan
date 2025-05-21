package ru.radzze.scan_impl.domain.models

data class RawScannedBooks(
    val x1: Int,
    val x2: Int,
    val x3: Int,
    val x4: Int,
    val y1: Int,
    val y2: Int,
    val y3: Int,
    val y4: Int,
    val possibleBooks: List<String>
)
