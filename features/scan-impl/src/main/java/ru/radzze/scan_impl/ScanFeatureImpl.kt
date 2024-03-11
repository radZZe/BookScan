package ru.radzze.scan_impl

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.scan_impl.ui.ResultScanScreen
import ru.radzze.scan_impl.ui.ScanScreen
import javax.inject.Inject

class ScanFeatureImpl @Inject constructor(
) : ScanFeatureApi {
    override val scanRoute: String
        get() = "scan"
    private val resultScanRoute = "resultScan"
    private val nestedScanGraph = "nestedScanGraph"



    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(scanRoute) {
            ScanScreen(onResultScanNavigate = {
                navController.navigate(resultScanRoute)
            })
        }

        navGraphBuilder.navigation(
            startDestination = resultScanRoute,
            route=nestedScanGraph
        ){
            composable(resultScanRoute){
                ResultScanScreen {
                    navController.navigate(scanRoute){
                        popUpTo(nestedScanGraph){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}