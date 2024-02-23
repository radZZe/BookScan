package ru.radzze.scan_api

import ru.radzze.core.FeatureApi

interface ScanFeatureApi:FeatureApi {
    val scanRoute:String
}