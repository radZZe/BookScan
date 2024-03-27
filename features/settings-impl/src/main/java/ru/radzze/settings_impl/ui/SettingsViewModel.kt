package ru.radzze.settings_impl.ui

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.settings_impl.data.UserData
import ru.radzze.settings_impl.domain.UserService
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val service: UserService
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getUserData()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    onNameChanged(body.name)
                    onSurnameChanged(body.surname)
                    configurations["Формат"]!!.value = body.isNeedBookFormat
                    configurations["Описание"]!!.value = body.isNeedDescription
                    configurations["Год издания"]!!.value = body.isNeedPublishYear
                    configurations["Язык"]!!.value = body.isNeedLanguage
                    configurations["Тип облолжки"]!!.value = body.isNeedCoverType
                    configurations["Количество страниц"]!!.value = body.isNeedPageNumber
                    configurations["Возрастное ограничение"]!!.value = body.isNeedAgeLimit
                }
                _isLoading.value = false
            }
        }
    }

    private val _name = mutableStateOf("Name")
    val name = _name

    private val _surname = mutableStateOf("Surname")
    val surname = _surname

    private val _isEditable = mutableStateOf(false)
    val isEditable = _isEditable

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri = _imageUri

    private val _isLoading = mutableStateOf<Boolean>(true)
    val isLoading = _isLoading

    val configurations = mapOf<String, MutableState<Boolean>>(
        "Описание" to mutableStateOf(false),
        "Год издания" to mutableStateOf(false),
        "Язык" to mutableStateOf(false),
        "Формат" to mutableStateOf(false),
        "Тип облолжки" to mutableStateOf(false),
        "Количество страниц" to mutableStateOf(false),
        "Возрастное ограничение" to mutableStateOf(false),
    )
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
    fun onSurnameChanged(newSurname: String) {
        _surname.value = newSurname
    }
    fun onIsEditChange() {
        _isEditable.value = !_isEditable.value
    }
    fun onCheckBoxStateChange(title: String) {
        configurations.forEach { pair ->
            if (pair.key == title) pair.value.value = !pair.value.value
        }
    }
    fun onImageChange(newUri: Uri) {
        _imageUri.value = newUri
    }
    fun saveData() {
        try {
            onIsEditChange()
            val userData = UserData(
                email = "",
                name = _name.value,
                surname = _surname.value,
                isNeedBookFormat = configurations["Формат"]!!.value,
                isNeedCoverType = configurations["Тип облолжки"]!!.value,
                isNeedDescription = configurations["Описание"]!!.value,
                isNeedLanguage = configurations["Язык"]!!.value,
                isNeedPageNumber = configurations["Количество страниц"]!!.value,
                isNeedPublishYear = configurations["Год издания"]!!.value,
                isNeedAgeLimit = configurations["Возрастное ограничение"]!!.value
            )
            viewModelScope.launch(Dispatchers.IO) {
                val result = service.updateUserData(userData)
                if (result.isSuccessful) {
                    println("Data sent successfully ${userData.toString()}")
                } else {
                    println("Failed ${userData.toString()}")
                }
            }
        } catch (_: Exception) {

        }
    }

}