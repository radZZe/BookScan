package ru.radzze.settings_api

import ru.radzze.core.FeatureApi

interface SettingsFeatureApi:FeatureApi {
    val settingsRoute:String
}