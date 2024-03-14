package ru.radzze.auth_api

import ru.radzze.core.FeatureApi

interface AuthFeatureApi:FeatureApi {
    val authRoute:String
    val codeVerificationRoute:String
}