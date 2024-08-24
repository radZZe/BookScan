package ru.radzze.scan_impl.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ru.radzze.core.models.NETWORK_STATUS
import ru.radzze.core.ui.TopBar
import ru.radzze.core.ui.shimmerEffect
import ru.radzze.scan_impl.R
import ru.radzze.scan_impl.domain.models.ScannedBook

@Composable
fun ResultScanScreen(
    onBackNavigate: () -> Unit,
    image: Uri?,
    viewModel: ResultScanScreenViewModel = hiltViewModel(),
    onFindBookNavigate:()->Unit,
) {
    if (viewModel.scanRequest == NETWORK_STATUS.NONE || viewModel.scanRequest == NETWORK_STATUS.LOADING) {
        viewModel.sendImageToScan(image.toString())
        ShimmerResultScreen()
    } else if (viewModel.scanRequest == NETWORK_STATUS.SUCCESS) {
        ResultScanContent(onBackNavigate, image, viewModel.scannedBook,onFindBookNavigate)
    } else {
        //TODO ОБРАБОТКА ОШИБКИ
    }
}

@Composable
fun BookItem(book: ScannedBook) {
    Box() {

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = book.title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "${book.author}, ", fontSize = 16.sp, color = Color.Gray)
            Text(text = book.genre, fontSize = 14.sp, color = Color.Gray)
        }
    }

}

@Composable
fun ShimmerBookItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(16.dp)
                .shimmerEffect()
        ) {}
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(14.dp)
                .shimmerEffect()
        ) {}
    }
}

@Composable
fun ShimmerResultScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar {}
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(400.dp)
                .clip(RoundedCornerShape(15))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(16.dp)
                .shimmerEffect()
        ) {}
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .shimmerEffect()
        ) {}
        Spacer(modifier = Modifier.height(5.dp))
        for (i in 0..10) {
            ShimmerBookItem()
        }
    }
}

@Composable
fun ResultScanContent(onBackNavigate: () -> Unit, image: Uri?, books: List<ScannedBook>,onFindBookNavigate: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
                .zIndex(2f), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(modifier = Modifier
                .weight(1f)
                .padding(7.dp, 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(238, 238, 238),
                    contentColor = Color.Black
                ),
                onClick = { onFindBookNavigate() }) {
                Text(text = "Добавить книгу", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(7.dp, 5.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Black),
                onClick = { /*TODO*/ }) {
                Text(text = "Сохранить", fontSize = 12.sp)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar {
                onBackNavigate()
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(400.dp)
                    .clip(RoundedCornerShape(15))
            ) {
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(id = R.drawable.book_image_mock),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Для удаления некорректного распознавания, проведите влево по результату",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            for (i in 0..books.size - 1) {
                SwipeToDeleteContainer(
                    item = books[i],
                    onDelete = {}
                ){
                    BookItem(it)
                }

            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}

@Composable
fun ErrorResultScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TODO
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                BackgroundSwipeDismiss(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackgroundSwipeDismiss(
    swipeDismissState: DismissState
) {
    val color = Color.Transparent
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
    }

}