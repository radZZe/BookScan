package ru.radzze.onboarding_impl.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth(),
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
                if (pagerState.currentPage == 4) {
                    onNavigateToLogin()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }

            })

    }
}


@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    item: OnboardingPage
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(0.8f),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier
                    .size((constraints.maxHeight - 50).dp)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = item.image),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.85f),
            text = item.description,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color(0,0,0)
        )
    }
}

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(modifier = modifier, onClick = { onClick() }) {
        Text(text = text,color = Color(0,0,0))
    }

}

