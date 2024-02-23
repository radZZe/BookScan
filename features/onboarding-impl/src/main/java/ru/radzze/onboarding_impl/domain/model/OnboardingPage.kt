package ru.radzze.onboarding_impl.domain.model

import androidx.annotation.DrawableRes
import ru.radzze.onboarding_impl.R

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val description: String,
    val btnText:String,
) {
    object First : OnboardingPage(
        image = R.drawable.first_screen,
        description = "Нажми на кнопку сканера, чтобы сделать фото",
        btnText = "Супер, понятно"
    )
    object Second : OnboardingPage(
        image = R.drawable.second_screen,
        description = "Список распознанных книг с возможностью просмотра информации",
        btnText = "Дальше"
    )
    object Third : OnboardingPage(
        image = R.drawable.third_screen,
        description = "Можно добавить книгу самостоятельно",
        btnText = "Понятно"
    )
    object Fourth : OnboardingPage(
        image = R.drawable.fourth_screen,
        description = "В разделе Библиотека можно посмотреть сканированные книги",
        btnText = "Дальше"
    )
    object Fifth : OnboardingPage(
        image = R.drawable.fifth_screen,
        description = "Редактирование конфигураций о книге",
        btnText = "Начать работу"
    )
}