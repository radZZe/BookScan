package ru.radzze.bookscan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.core.FeatureApi
import ru.radzze.onboarding_api.OnboardingFeatureApi

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authFeatureApi: AuthFeatureApi,
    onboardingFeatureApi: OnboardingFeatureApi

) {

    NavHost(
        navController = navController,
        startDestination = onboardingFeatureApi.onboardingRoute
    ) {
        register(
            onboardingFeatureApi,
            navController = navController,
            modifier = modifier
        )
        register(
            authFeatureApi,
            navController = navController,
            modifier = modifier
        )
    }

}

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    featureApi.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier
    )
}