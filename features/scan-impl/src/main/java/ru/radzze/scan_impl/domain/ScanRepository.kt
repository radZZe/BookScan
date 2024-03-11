package ru.radzze.scan_impl.domain

interface ScanRepository {
    fun sendImageToScan(onSuccess:()->Unit)
}