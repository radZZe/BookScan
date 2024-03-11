package ru.radzze.bookscan

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.bookscan.navigation.AppNavGraph
import ru.radzze.bookscan.navigation.BottomBarXml
import ru.radzze.bookscan.navigation.BottomTabs
import ru.radzze.bookscan.ui.theme.BookScanTheme
import ru.radzze.library_api.LibraryFeatureApi
import ru.radzze.onboarding_api.OnboardingFeatureApi
import ru.radzze.onboarding_impl.domain.OnboardingRepository
import ru.radzze.scan_api.ScanFeatureApi
import ru.radzze.settings_api.SettingsFeatureApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authFeatureApi: AuthFeatureApi

    @Inject
    lateinit var onboardingFeatureApi: OnboardingFeatureApi

    @Inject
    lateinit var scanFeatureApi: ScanFeatureApi

    @Inject
    lateinit var settingsFeatureApi: SettingsFeatureApi

    @Inject
    lateinit var libraryFeatureApi: LibraryFeatureApi


    private val mViewModel: MainActivityViewModel by viewModels()


    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabs = listOf(
            BottomTabs(
                R.string.settings_screen_title,
                R.drawable.settings_icon,
                settingsFeatureApi.settingsRoute

            ),
            BottomTabs(
                R.string.scan_screen_title, R.drawable.scan_icon, scanFeatureApi.scanRoute
            ),
            BottomTabs(
                R.string.library_screen_title,
                R.drawable.library_icon,
                libraryFeatureApi.libraryRoute
            )
        )
        var onboardingState = false
        lifecycleScope.launch(Dispatchers.IO) {
            onboardingState = mViewModel.getOnboardingState()
        }
        setContent {
            BookScanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val bottomNavTabs = remember { tabs }
                    Scaffold(
                        bottomBar = { BottomBarXml(navController = navController, bottomNavTabs) }
                    ) {

                        AppNavGraph(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            authFeatureApi = authFeatureApi,
                            onboardingFeatureApi = onboardingFeatureApi,
                            settingsFeatureApi = settingsFeatureApi,
                            scanFeatureApi = scanFeatureApi,
                            libraryFeatureApi = libraryFeatureApi,
                            onBoardingState = onboardingState
                        )

                    }
                }
            }
        }
    }
}
