package ru.radzze.core.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.radzze.core.R

@Composable
fun TopBar(
    onBackNavigate:()->Unit,
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)) {
        Icon(painter = painterResource(R.drawable.arrow_back),contentDescription = null,modifier = Modifier.clickable {
            onBackNavigate()
        })
    }
}

@Composable
fun FilterTopBar(){
    // TODO
}

fun Modifier.shimmerEffect():Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat() ,
        targetValue = 2 * size.width.toFloat() ,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(180, 180, 180, 255),
                Color(146, 146, 146, 255),
                Color(180, 180, 180, 255)
            ),
            start = Offset(startOffsetX,0f),
            end = Offset(startOffsetX + size.width.toFloat(),size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}