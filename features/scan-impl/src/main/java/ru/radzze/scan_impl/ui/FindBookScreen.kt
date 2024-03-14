package ru.radzze.scan_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.radzze.core.ui.TopBar
import ru.radzze.scan_impl.R

@Composable
fun FindBookScreen(
    onBackNavigate:()->Unit,
){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar {
            onBackNavigate()
        }
        Image(painter = painterResource(id = R.drawable.find_book_icon), contentDescription = null)
        Text(text = "Введите название и уникальный номер книжного издания", fontSize = 14.sp, textAlign = TextAlign.Center)

    }
}