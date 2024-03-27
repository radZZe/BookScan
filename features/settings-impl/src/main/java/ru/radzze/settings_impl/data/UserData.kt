package ru.radzze.settings_impl.data

import androidx.compose.runtime.MutableState

data class UserData(
    val email: String,
    val name: String,
    val surname: String,
    val isNeedDescription: Boolean,
    val isNeedPublishYear: Boolean,
    val isNeedLanguage: Boolean,
    val isNeedBookFormat: Boolean,
    val isNeedCoverType: Boolean,
    val isNeedPageNumber: Boolean,
    val isNeedAgeLimit: Boolean
)
