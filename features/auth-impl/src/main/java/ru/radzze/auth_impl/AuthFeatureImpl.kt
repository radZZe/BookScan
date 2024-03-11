package ru.radzze.auth_impl

import android.content.Intent
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.auth_impl.ui.AuthScreen
import ru.radzze.scan_api.ScanFeatureApi
import javax.inject.Inject


class AuthFeatureImpl @Inject constructor(
    private val scanFeatureApi: ScanFeatureApi
):AuthFeatureApi {
    override val authRoute: String
        get() = "auth"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(authRoute) {
            AuthScreen(navigateToMainGraph = {
                navController.navigate(scanFeatureApi.scanRoute) {
                    popUpTo(authRoute) { inclusive = true }
                }
            })
        }
    }
}