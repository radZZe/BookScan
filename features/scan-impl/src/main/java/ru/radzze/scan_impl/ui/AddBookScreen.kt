@file:OptIn(ExperimentalMaterial3Api::class)

package ru.radzze.scan_impl.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.radzze.core.ui.CustomTextField
import ru.radzze.core.ui.TopBar
import ru.radzze.scan_impl.R
import ru.radzze.scan_impl.domain.models.CheckBox

@Composable
fun AddBookScreen(
    viewModel: AddBookScreenViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar {

        }
        BookImage()
        Spacer(modifier = Modifier.height(15.dp))
        BookField(viewModel)
        Button(
            onClick = { viewModel.saveAddedBook() },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Сохранить", color = Color.Black)
        }
    }
}

@Composable
fun BookImage() {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .heightIn(100.dp, 230.dp)
            .clip(
                RoundedCornerShape(15)
            )
            .background(Color(239, 235, 222))

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (selectedImageUri == null) {
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(id = R.drawable.add_book),
                    contentDescription = null
                )
            } else {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookField(
    viewModel: AddBookScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CustomTextField(value = viewModel.title, isError = false, onValueChange = {
            viewModel.onTitleChanged(it)
        }, placeholder = "Название")
        CustomTextField(value = viewModel.author, isError = false, onValueChange = {
            viewModel.onAuthorChanged(it)
        }, placeholder = "Автор")
        CustomDropDown(value = viewModel.getGenresString(), isError = false, onActive = {
            viewModel.getGenres()
            viewModel.onChangeGenresBottomSheetState()
        }, placeholder = "Жанры")
        CustomTextField(value = viewModel.ISBN, isError = false, onValueChange = {
            viewModel.onISBNChanged(it)
        }, placeholder = "ISBN")
        CustomTextField(
            value = viewModel.publisher,
            isError = false,
            onValueChange = {
                viewModel.onPublisherChanged(it)
            },
            placeholder = "Издательство"
        )
        CustomDropDown(value = viewModel.year, isError = false, onActive = {
            viewModel.onChangeYearBottomSheetState()
        }, placeholder = "Год издания")
        CustomDropDown(value = viewModel.language, isError = false, onActive = {
            viewModel.onChangeLanguageBottomSheetState()
        }, placeholder = "Язык")
        CustomDropDown(value = viewModel.format, isError = false, onActive = {
            viewModel.onChangeFormatBottomSheetState()
        }, placeholder = "Формат")
        CustomDropDown(value = viewModel.coverType, isError = false, onActive = {
            viewModel.onChangeCoverTypeBottomSheetState()
        }, placeholder = "Тип переплёта")
        CustomTextField(
            value = viewModel.pages,
            isError = false,
            onValueChange = {
                viewModel.onPagesChanged(it)
            },
            placeholder = "Количество страниц"
        )
        CustomTextField(
            value = viewModel.ageLimit,
            isError = false,
            onValueChange = {
                viewModel.onAgeLimitChanged(it)
            },
            placeholder = "Возрастное ограничение"
        )
        CustomTextField(value = viewModel.description, isError = false, onValueChange = {
            viewModel.onDescriptionChanged(it)
        }, placeholder = "Описание")
    }
    if (viewModel.showBottomSheetLanguage) {
        LanguageBottomSheet(viewModel.languageList,
            { viewModel.onChangeLanguageBottomSheetState() },
            { data, ind ->
                viewModel.changeLanguageState(data, ind)
            })
    }
    if (viewModel.showBottomSheetYear) {
        YearBottomSheet(
            onDismissRequest = { viewModel.onChangeYearBottomSheetState() },
            onYearChanged = { viewModel.onYearChanged(it) })
    }
    if (viewModel.showBottomSheetFormat) {
        FormatBottomSheet(viewModel.formatList, {
            viewModel.onChangeFormatBottomSheetState()
        }, { data, ind ->
            viewModel.changeFormatState(data, ind)
        })
    }
    if (viewModel.showBottomSheetGenres) {
        GenresBottomSheet(
            viewModel.genres,
            onDismissRequest = { viewModel.onChangeGenresBottomSheetState() },
            { data, ind ->
                viewModel.changeGenresState(data, ind)
            }
        )
    }
    if (viewModel.showBottomSheetCoverType) {
        CoverTypeBottomSheet(viewModel.coverTypeList, {
            viewModel.onChangeCoverTypeBottomSheetState()
        }, { data, ind ->
            viewModel.changeCoverTypeState(data, ind)
        })
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
    value: String,
    isError: Boolean,
    onActive: () -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        readOnly = true,
        value = value,
        onValueChange = {},
        label = { Text(text = placeholder) },
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        ),
        placeholder = {
            if (value.isEmpty()) {
                Text(
                    text = "",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            errorIndicatorColor = Color.Red,
            focusedTrailingIconColor = Color.LightGray,
            unfocusedTrailingIconColor = Color.Transparent,
            focusedLabelColor = Color.Gray,
            unfocusedSupportingTextColor = Color.LightGray,
        ),
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.drop_down_arrow),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onActive()
//                            onValueChange("")
                    }
            )

        },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
    )
}


@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    data: SnapshotStateList<CheckBox>,
    onDismissRequest: () -> Unit,
    onCheckBoxChange: (SnapshotStateList<CheckBox>, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            TopBarModalBottomSheet("Язык")
            data.forEachIndexed { index, checkBox ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                    Checkbox(checked = checkBox.state, onCheckedChange = {
                        onCheckBoxChange(data, index)
                    })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = checkBox.label)
                }
                Spacer(modifier = Modifier.height(3.dp))
            }
        }

    }

}

@Composable
fun TopBarModalBottomSheet(title: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.close_circle),
            contentDescription = null
        )
        Text(modifier = Modifier.align(Alignment.Center), text = title)
        Text(modifier = Modifier.align(Alignment.CenterEnd), text = "Сбросить", color = Color.Gray)
    }
}

@Composable
fun YearBottomSheet(onDismissRequest: () -> Unit, onYearChanged: (String) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            TopBarModalBottomSheet("Год издания")
            YearOfPublish {
                onYearChanged(it)
            }
        }

    }


}

@Composable
fun FormatBottomSheet(
    data: SnapshotStateList<CheckBox>,
    onDismissRequest: () -> Unit,
    onCheckBoxChange: (SnapshotStateList<CheckBox>, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            TopBarModalBottomSheet("Формат")
            data.forEachIndexed { index, checkBox ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                    Checkbox(checked = checkBox.state, onCheckedChange = {
                        onCheckBoxChange(data, index)
                    })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = checkBox.label)
                }
                Spacer(modifier = Modifier.height(3.dp))
            }
        }

    }

}

@Composable
fun CoverTypeBottomSheet(
    data: SnapshotStateList<CheckBox>,
    onDismissRequest: () -> Unit,
    onCheckBoxChange: (SnapshotStateList<CheckBox>, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            TopBarModalBottomSheet("Тип переплёта")
            data.forEachIndexed { index, checkBox ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                    Checkbox(checked = checkBox.state, onCheckedChange = {
                        onCheckBoxChange(data, index)
                    })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = checkBox.label)
                }
                Spacer(modifier = Modifier.height(3.dp))
            }
        }

    }

}

@Composable
fun GenresBottomSheet(
    data: SnapshotStateList<CheckBox>,
    onDismissRequest: () -> Unit,
    onCheckBoxChange: (SnapshotStateList<CheckBox>, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TopBarModalBottomSheet("Жанры")
            data.forEachIndexed { index, checkBox ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                    Checkbox(checked = checkBox.state, onCheckedChange = {
                        onCheckBoxChange(data, index)
                    })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = checkBox.label)
                }
                Spacer(modifier = Modifier.height(3.dp))
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> InfiniteCircularList(
    itemHeight: Dp,
    numberOfDisplayedItems: Int = 5,
    items: List<T>,
    initialItem: T,
    itemScaleFact: Float = 1.5f,
    textStyle: TextStyle,
    textColor: Color,
    onItemSelected: (item: T) -> Unit = { _ -> }
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember {
        mutableStateOf(items.size - 1)
    }
    var itemsState by remember {
        mutableStateOf(items)
    }
    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem) - 1
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        itemsState = items
        lastSelectedIndex = targetIndex % items.size
        scrollState.scrollToItem(targetIndex)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight * numberOfDisplayedItems),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState
        )
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { i ->
                val item = itemsState[i % itemsState.size]
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .height(itemHeight)
                        .fillMaxWidth()
                        .background(if (lastSelectedIndex == i) Color(0xFFFCE181) else Color.Transparent)
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - itemHalfHeight
                            val parentHalfHeight = (itemHalfHeight * numberOfDisplayedItems)
                            val isSelected =
                                (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                            val index = i - 1
                            if (isSelected && lastSelectedIndex != index) {
                                lastSelectedIndex = index
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.toString(),
                        style = textStyle,
                        color = textColor,
                        fontSize = if (lastSelectedIndex == i) {
                            textStyle.fontSize * itemScaleFact
                        } else {
                            textStyle.fontSize
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                if (lastSelectedIndex == i) onItemSelected(item)
            }
        )
    }
}

// Usage example
@Composable
fun YearOfPublish(
    labelArrange: Arrangement.Horizontal = Arrangement.Start,
    onYearChanged: (String) -> Unit,
) {
    val yearsList = (1990..2024).map { it.toString() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            horizontalArrangement = labelArrange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Год издания",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        InfiniteCircularList(
            itemHeight = 24.dp,
            items = yearsList,
            initialItem = "2024",
            textColor = Color.Black,
            textStyle = TextStyle(
                fontSize = 12.sp,
            ),
            onItemSelected = { onYearChanged(it) }
        )
    }
}