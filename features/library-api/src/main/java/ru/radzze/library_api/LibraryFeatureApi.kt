package ru.radzze.library_api

import ru.radzze.core.FeatureApi

interface LibraryFeatureApi:FeatureApi {
    val libraryRoute:String
}