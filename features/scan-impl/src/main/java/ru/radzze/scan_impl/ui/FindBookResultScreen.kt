package ru.radzze.scan_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.radzze.core.ui.TopBar
import ru.radzze.scan_impl.R

@Composable
fun FindBookResultScreen(
    onBackNavigate: () -> Unit,
    onAddBookNavigate:()->Unit,
    viewModel: FindBookResultViewModel = hiltViewModel()
) {
    Column() {
        TopBar() {
            onBackNavigate()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(viewModel.dataList) { index, el ->
                    FindBookItem(
                        image = el.image,
                        title = el.title,
                        author = el.author,
                        isSelected = el.isChecked
                    ) {
                        viewModel.changeItemState(index)
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onAddBookNavigate()}, modifier = Modifier.padding(7.dp, 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(238, 238, 238),
                        contentColor = Color.Black
                    ),
                ) {
                    Text(text = "Ручной ввод")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Сохранить", color = Color.Black)
                }
            }
        }
    }


}


@Composable
fun FindBookItem(
    image: String?,
    title: String,
    author: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .clip(RoundedCornerShape(15))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 5.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (image == null) {
                Image(
                    modifier = Modifier.sizeIn(maxHeight = 100.dp, minHeight = 50.dp),
                    painter = painterResource(id = R.drawable.mock_book),
                    contentDescription = null
                )
            } else {
                //Image(painter = painterResource(id = R.drawable.find_book_icon), contentDescription = null)
            }

            Column() {
                Text(
                    text = title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(text = author, color = Color.Gray, fontSize = 12.sp)
            }

            RadioButton(selected = isSelected, onClick = { onClick() })
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}