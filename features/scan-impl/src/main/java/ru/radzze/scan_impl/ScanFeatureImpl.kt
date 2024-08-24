package ru.radzze.scan_impl

import android.net.Uri
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.scan_impl.ui.AddBookScreen
import ru.radzze.scan_impl.ui.FindBookResultScreen
import ru.radzze.scan_impl.ui.FindBookScreen
import ru.radzze.scan_impl.ui.ResultScanScreen
import ru.radzze.scan_impl.ui.ScanScreen
import javax.inject.Inject

class ScanFeatureImpl @Inject constructor(
) : ScanFeatureApi {
    override val scanRoute: String
        get() = "scan"
    private val resultScanRoute = "resultScan"
    private val findBookRoute = "findBook"
    private val findBookResult = "findBookResult"
    private val nestedScanGraph = "nestedScanGraph"
    private val addBookRoute = "addBookScreen"
    private val imageArg = "image"


    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(scanRoute) {
            ScanScreen(onResultScanNavigate = {
                val route = "$resultScanRoute/$it"
                navController.navigate(route)
            })
        }

        navGraphBuilder.navigation(
            startDestination = "$resultScanRoute/{$imageArg}",
            route = nestedScanGraph
        ) {
            composable(
                "$resultScanRoute/{$imageArg}",
                arguments = listOf(navArgument(imageArg) { this.type = NavType.StringType })
            ) { backStackEntry ->
                val encodeUri = backStackEntry.arguments?.getString(imageArg)?.replace('|', '%')
                val decodeUri = Uri.parse(encodeUri)
                ResultScanScreen(
                    image = decodeUri,
                    onBackNavigate = {
                        navController.navigate(scanRoute) {
                            popUpTo(nestedScanGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onFindBookNavigate = {
                          navController.navigate(findBookRoute)
                    })
            }

            composable(findBookRoute)
            {
                FindBookScreen(
                    onBackNavigate = {navController.popBackStack()},
                    onResultNavigate = {navController.navigate(findBookResult)}
                )
            }

            composable(findBookResult){
                FindBookResultScreen(onBackNavigate = {
                    navController.popBackStack()
                },
                    onAddBookNavigate={
                        navController.navigate(addBookRoute)
                    })
            }

            composable(addBookRoute){
                AddBookScreen()
            }
        }
    }
}