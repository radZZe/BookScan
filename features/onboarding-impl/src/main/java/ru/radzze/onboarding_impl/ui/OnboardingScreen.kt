package ru.radzze.onboarding_impl.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.radzze.onboarding_impl.R
import ru.radzze.onboarding_impl.domain.model.OnboardingPage


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnboardingPage.First,
        OnboardingPage.Second,
        OnboardingPage.Third,
        OnboardingPage.Fourth,
        OnboardingPage.Fifth,
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(0.95f).fillMaxSize(0.8f),
            count = 5,
            state = pagerState
        ) { pos ->
            OnboardingContent(item = pages[pos])
        }
        HorizontalPagerIndicator(
            modifier = Modifier,
            pagerState = pagerState
        )
        OnboardingButton(
            modifier = Modifier,
            text = pages[pagerState.currentPage].btnText,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            })

    }
}


@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    item: OnboardingPage
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = item.image),
            contentDescription = null
        )
        Text(text = item.description)
    }
}

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(modifier = modifier, onClick = { onClick() }) {
        Text(text = text)
    }

}