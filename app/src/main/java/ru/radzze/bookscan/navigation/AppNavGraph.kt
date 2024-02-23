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
import ru.radzze.library_api.LibraryFeatureApi
import ru.radzze.onboarding_api.OnboardingFeatureApi
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.settings_api.SettingsFeatureApi

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authFeatureApi: AuthFeatureApi,
    onboardingFeatureApi: OnboardingFeatureApi,
    scanFeatureApi: ScanFeatureApi,
    settingsFeatureApi: SettingsFeatureApi,
    libraryFeatureApi: LibraryFeatureApi,
    onBoardingState:Boolean

) {

    NavHost(
        navController = navController,
        startDestination = if(onBoardingState) authFeatureApi.authRoute else onboardingFeatureApi.onboardingRoute
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
        register(
            scanFeatureApi,
            navController = navController,
            modifier = modifier
        )
        register(
            settingsFeatureApi,
            navController = navController,
            modifier = modifier
        )
        register(
            libraryFeatureApi,
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