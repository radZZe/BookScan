package ru.radzze.auth_impl

import android.content.Intent
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.auth_impl.ui.AuthScreen
import ru.radzze.auth_impl.ui.CodeVerificationScreen
import ru.radzze.scan_api.ScanFeatureApi
import javax.inject.Inject


class AuthFeatureImpl @Inject constructor(
    private val scanFeatureApi: ScanFeatureApi
) : AuthFeatureApi {
    override val authRoute: String
        get() = "auth"

    override val codeVerificationRoute: String
        get() = "code_verification"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(authRoute) {
            AuthScreen(navigateToCodeVerification = {
                navController.navigate("$codeVerificationRoute/$it")
            })
        }


        navGraphBuilder.composable(
            route = "$codeVerificationRoute/{email}",
            arguments = listOf(
                navArgument(name = "email") { type = NavType.StringType }
            )
        ) {
            val email = it.arguments?.getString("email") ?: ""
            CodeVerificationScreen(
                email = email,
                onNavigateToScanFeature = {
                    navController.navigate(scanFeatureApi.scanRoute) {
                        popUpTo(authRoute) { inclusive = true }
                    }
                },
                onBackNavigate = {
                    navController.popBackStack()
                }
            )
        }
    }
}