package ru.radzze.onboarding_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.onboarding_api.OnboardingFeatureApi
import ru.radzze.onboarding_impl.ui.OnboardingScreen
import javax.inject.Inject

class OnboardingFeatureImpl @Inject constructor(
    private val authFeatureApi:AuthFeatureApi
):OnboardingFeatureApi {
    override val onboardingRoute: String
        get() = "onboarding"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(onboardingRoute) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(authFeatureApi.authRoute) {
                        popUpTo(onboardingRoute) { inclusive = true }
                    }
                }
            )
        }
    }

}