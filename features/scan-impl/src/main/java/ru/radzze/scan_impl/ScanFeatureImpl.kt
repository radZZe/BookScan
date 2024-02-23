package ru.radzze.scan_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.scan_impl.ui.ScanScreen
import javax.inject.Inject

class ScanFeatureImpl @Inject constructor():ScanFeatureApi {
    override val scanRoute: String
        get() = "scan"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(scanRoute) {
            ScanScreen()
        }
    }
}