package ru.radzze.settings_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.settings_api.SettingsFeatureApi
import ru.radzze.settings_impl.ui.SettingsScreen
import javax.inject.Inject

class SettingsFeatureImpl @Inject constructor():SettingsFeatureApi {
    override val settingsRoute: String
        get() = "settings"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(settingsRoute) {
            SettingsScreen()
        }
    }
}