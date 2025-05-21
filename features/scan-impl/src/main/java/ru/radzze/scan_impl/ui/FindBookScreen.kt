package ru.radzze.scan_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.radzze.core.ui.CustomTextField
import ru.radzze.core.ui.TopBar
import ru.radzze.scan_impl.R

@Composable
fun FindBookScreen(
    onBackNavigate: () -> Unit,
    onResultNavigate: (title:String,isbn:String) -> Unit,
    viewModel: FindBookViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar {
            onBackNavigate()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(130.dp),
                    painter = painterResource(id = R.drawable.find_book_icon),
                    contentDescription = null
                )
                Text(
                    text = "Введите название и уникальный номер книжного издания",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomTextField(
                    value = viewModel.name.value,
                    isError = false,
                    onValueChange = {
                        viewModel.onNameChanged(it)
                    },
                    placeholder = "Название"
                )
                CustomTextField(value = viewModel.isbn.value, isError = false, onValueChange = {
                    viewModel.onIsbnChanged(it)
                }, placeholder = "ISBN")
            }

            Button(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f), onClick = {
                onResultNavigate(viewModel.name.value,viewModel.isbn.value)
            }) {
                Text(text = "Найти книгу", color = Color.Black)
            }

        }
    }


}