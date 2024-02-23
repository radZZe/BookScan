package ru.radzze.library_impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.radzze.library_api.LibraryFeatureApi
import ru.radzze.library_impl.ui.LibraryScreen
import javax.inject.Inject

class LibraryFeatureImpl @Inject constructor(): LibraryFeatureApi {
    override val libraryRoute: String
        get() = "library"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(libraryRoute){
            LibraryScreen()
        }
    }
}