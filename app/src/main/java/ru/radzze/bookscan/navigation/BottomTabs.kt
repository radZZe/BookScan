package ru.radzze.bookscan.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
)

