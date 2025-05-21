package ru.radzze.library_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.radzze.library_impl.R
import ru.radzze.library_impl.data.FilterRequest
import coil.compose.rememberAsyncImagePainter
import ru.radzze.library_impl.domain.models.GetUserBooksDto

@Composable
fun LibraryScreen(
    onNavigateToFilter: () -> Unit,
//    filterRequest: FilterRequest?,
    viewModel: LibraryViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setFalse()
        viewModel.clearFindedBook()
        viewModel.getUserBooks()
//        viewModel.initFilterRequest(filterRequest)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val modifier = Modifier.padding(bottom = 60.dp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        SearchTextField(
            value = viewModel.request.value,
            isClickedChanged = { viewModel.onIsSearchClickedChange() }
        ) { viewModel.onRequestChanged(it) }
        OptionsRow(viewModel = viewModel, onNavigateToFilter = { onNavigateToFilter() })
        if (!viewModel.isSearchClicked.value) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Ваши последние запросы:",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                LazyColumn(modifier = modifier) {
                    items(viewModel.lastRequests) { request ->
                        Text(
                            text = request,
                            color = Color.Black,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .clickable {
                                    viewModel.onRequestChanged(request)
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                        )
                    }
                }
            }
        } else {
            if (viewModel.libraryBooks.isEmpty()) {
                Text(
                    text = "Библиотека пуста. Для отображения, отсканируйте книгу в разделе “Сканер”",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
            } else {
                if (viewModel.columnMode.value) {
                    LazyColumn(modifier = modifier) {
                        items(viewModel.libraryBooks) { book ->
                            BookItemColumn(book)
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = modifier
                    ) {
                        items(viewModel.libraryBooks) { book ->
                            BookItemGrid(book)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value: String,
    placeholder: String = "Поиск",
    isClickedChanged: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusManager.clearFocus()
    }

    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.search),
                contentDescription = null,
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    painterResource(id = R.drawable.close_circle),
                    contentDescription = null,
                    modifier = Modifier.clickable { onValueChanged("") }
                )
            }
        },
        placeholder = { Text(placeholder) },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester)
            .onFocusChanged { isClickedChanged() }
    )
}

@Composable
fun OptionsRow(
    onNavigateToFilter: () -> Unit,
    viewModel: LibraryViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.grid_transparent),
                contentDescription = null,
                tint = if (viewModel.gridMode.value) Color.Black else Color.LightGray,
                modifier = Modifier
                    .size(22.dp)
                    .clickable { viewModel.onGridActive() }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.column_bold),
                contentDescription = null,
                tint = if (viewModel.columnMode.value) Color.Black else Color.LightGray,
                modifier = Modifier
                    .size(22.dp)
                    .clickable { viewModel.onColumnActive() }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Transparent)
                .size(28.dp)
                .clickable { onNavigateToFilter() }
        )
    }
}

@Composable
fun BookItemColumn(book:
                   GetUserBooksDto
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(book.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .height(100.dp)
                .width(60.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(100.dp)
        ) {
            Text(
                text = book.name!!,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.author!!,
                color = Color.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.isbn!!,
                color = Color.LightGray,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BookItemGrid(book: GetUserBooksDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(book.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .height(240.dp)
                .fillMaxWidth()
        )
        Text(
            text = book.name!!,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
        Text(
            text = book.author!!,
            color = Color.LightGray,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
        )
    }
}