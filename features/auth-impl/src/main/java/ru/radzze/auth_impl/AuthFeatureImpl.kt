package ru.radzze.auth_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.auth_impl.ui.AuthScreen
import javax.inject.Inject


class AuthFeatureImpl @Inject constructor():AuthFeatureApi {
    override val authRoute: String
        get() = "auth"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(authRoute) {
            AuthScreen()
        }
    }
}