package ru.radzze.bookscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.radzze.auth_api.AuthFeatureApi
import ru.radzze.bookscan.navigation.AppNavGraph
import ru.radzze.bookscan.ui.theme.BookScanTheme
import ru.radzze.onboarding_api.OnboardingFeatureApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authFeatureApi: AuthFeatureApi
    @Inject
    lateinit var onboardingFeatureApi: OnboardingFeatureApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookScanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navController =navController , authFeatureApi =authFeatureApi , onboardingFeatureApi =onboardingFeatureApi )
                }
            }
        }
    }
}
