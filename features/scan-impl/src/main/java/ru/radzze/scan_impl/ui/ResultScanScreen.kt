package ru.radzze.scan_impl.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.radzze.core.ui.TopBar

@Composable
fun ResultScanScreen(onBackNavigate:()->Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            onBackNavigate()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(20) {
                    BookItem()
                }
            }
        }


    }

}

@Composable
fun BookItem(title: String = "title", author: String = "author", genre: String = "genre") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "$author, ", fontSize = 16.sp, color = Color.Gray)
            Text(text = genre, fontSize = 14.sp, color = Color.Gray)
        }
    }

}

@Composable
@Preview
fun PreviewBookItem() {
    BookItem()
}