package ru.radzze.library_impl.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.radzze.library_impl.R
import ru.radzze.library_impl.data.CheckBox
import ru.radzze.library_impl.data.FilterRequest
import ru.radzze.library_impl.data.GridItem


@Composable
fun FilterScreen(
    onNavigateBack: (FilterRequest?) -> Unit,
    viewModel: FilterViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
    ) {
        SearchTextField(
            value = viewModel.required.value,
            onValueChanged = { viewModel.onRequiredChanged(it) }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onNavigateBack(null) }
            )
            Icon(
                painterResource(id = R.drawable.filter),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFFCE181))
                    .size(32.dp)
                    .padding(4.dp)
                    .clickable { onNavigateBack(null) }
            )
        }
        ScanDateRadios(
            viewModel.radioOptions
        ) { viewModel.onRadioSwitch(it) }
        Spacer(modifier = Modifier.height(8.dp))
        Genres(onShowGenres = { viewModel.onShowGenres() })
        Spacer(modifier = Modifier.height(8.dp))
        GridSearch(
            label = "Автор",
            data = viewModel.authors,
            tfValue = viewModel.author.value,
            onGridItemChanged = { mode, index ->
                viewModel.onGridItemChanged(mode, index)
            },
            showMoreState = viewModel.showMoreAuthors.value,
            showMore = { viewModel.showMoreAuthors() },
            onValueChanged = { viewModel.onAuthorChanged(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        YearOfPublish() { viewModel.onYearChanged(it) }
        Spacer(modifier = Modifier.height(8.dp))
        GridSearch(
            label = "Издательство",
            data = viewModel.distributors,
            tfValue = viewModel.distributor.value,
            onGridItemChanged = { mode, index ->
                viewModel.onGridItemChanged(mode, index)
            },
            showMore = { viewModel.showMoreDistributors() },
            showMoreState = viewModel.showMoreDistributors.value,
            onValueChanged = { viewModel.onDistributorChanged(it) }
        )
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)
    ) {
        FloatingActionButton(
            onClick = { onNavigateBack(viewModel.formFilter()) },
            shape = RoundedCornerShape(25.dp),
            containerColor = Color(0xFFFCE181),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(text = "Применить фильтрацию", color = Color.Black, fontSize = 14.sp)
        }
    }
    if (viewModel.showGenres) {
        GenresBottomSheet(
            data = viewModel.genresCheckbox,
            onDismissRequest = { viewModel.onShowGenres() },
            onClearGenres = { viewModel.clearGenres(viewModel.genresCheckbox) },
            onCheckBoxChange = { data, ind ->
                viewModel.changeGenresState(data, ind)
            }
        )
    }
}

@Composable
fun ScanDateRadios(
    options: Map<String, MutableState<Boolean>>,
    onRadioSwitch: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Дата сканирования",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        options.forEach { pair ->
            CustomRadioButton(
                label = pair.key,
                isSelected = pair.value.value
            ) { onRadioSwitch(pair.key) }
        }
    }
}

@Composable
fun CustomRadioButton(
    label: String,
    isSelected: Boolean,
    onSwitch: (String) -> Unit
) {
    val buttonColour = if (isSelected) Color(0xFFFCE181) else Color.Gray
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, bottom = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(buttonColour)
                .clickable { onSwitch(label) }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(21.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFAF8F2))
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFCE181))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Genres(
    onShowGenres: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Жанры",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onShowGenres() }
        ) {
            Text(
                text = "Выбрать жанры",
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Icon(
                painterResource(id = R.drawable.arrow_down),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun GridSearch(
    label: String,
    data: List<GridItem>,
    tfValue: String,
    showMoreState: Boolean,
    showMore: () -> Unit,
    onValueChanged: (String) -> Unit,
    onGridItemChanged: (String, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        SearchTextField(value = tfValue, onValueChanged = { onValueChanged(it) })
        Spacer(modifier = Modifier.height(8.dp))
        DynamicGrid(label, data, showMoreState, { showMore() }) { title, index ->
            onGridItemChanged(title, index)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DynamicGrid(
    mode: String,
    data: List<GridItem>,
    showMoreState: Boolean,
    showMore: () -> Unit,
    onGridItemChanged: (String, Int) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val more = data.size - 16
        data.forEachIndexed { index, item ->
            if (index <= 16 || showMoreState) {
                GridItem(
                    text = item.title,
                    isChecked = item.state.value,
                ) { onGridItemChanged(mode, index) }
            }
        }
        if (more > 0 && !showMoreState) {
            GridItem(
                selectedColor = Color(0xFFEFEBDE),
                text = "+$more",
                isChecked = true
            ) { showMore() }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GridItem(
    text: String,
    isChecked: Boolean = false,
    selectedColor: Color = Color(0xFFFCE181),
    onClick: () -> Unit?
) {
    val interactionSource = remember { MutableInteractionSource() }
    AnimatedContent(
        targetState = isChecked,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 150)) with
                    fadeOut(animationSpec = tween(durationMillis = 150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                IntSize(initialSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                IntSize(targetSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }


        },
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ) { onClick() },
        label = ""
    ) { state ->
        val fontSize = 14
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 6.dp, bottom = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (state) selectedColor else Color.White)
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontSize = fontSize.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
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

@Composable
fun TopBarModalBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    onClearGenres: (SnapshotStateList<CheckBox>) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onDismissRequest() },
            painter = painterResource(id = R.drawable.close_circle),
            contentDescription = null,
        )
        Text(modifier = Modifier.align(Alignment.Center), text = title)
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onClearGenres },
            text = "Сбросить", color = Color.Gray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenresBottomSheet(
    data: SnapshotStateList<CheckBox>,
    onDismissRequest: () -> Unit,
    onClearGenres: (SnapshotStateList<CheckBox>) -> Unit,
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
            TopBarModalBottomSheet("Жанры", { onDismissRequest() }, { onClearGenres(data) })
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
            Button(
                onClick = { onDismissRequest() },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Применить", color = Color.Black, fontSize = 14.sp)
            }
        }
    }
}