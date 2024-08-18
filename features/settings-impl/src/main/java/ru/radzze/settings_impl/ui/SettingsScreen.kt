package ru.radzze.settings_impl.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ru.radzze.core.ui.shimmerEffect
import ru.radzze.settings_impl.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val editable = viewModel.isEditable.value
    val indication = rememberRipple()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.onImageChange(it)
            }
        }
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 28.dp, end = 28.dp, top = 28.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape)
                    .background(if (editable) Color(0xFFFCE181) else Color.Transparent)
                    .clickable { viewModel.onIsEditChange() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pen),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (viewModel.isLoading.value) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .background(Color(0xFFEFEBDE))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = if (editable) indication else null
                        ) { if (editable) galleryLauncher.launch("image/*") }
                ) {
                    viewModel.imageUri.value?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = uri
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } ?: Image(
                        painterResource(
                            id = if (editable) R.drawable.edit_avatar else R.drawable.avatar_placeholder
                        ),
                        contentDescription = null
                    )
                }
            }
            if (editable) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Добавить аватар",
                    fontSize = 16.sp,
                    color = Color(0xFF272727),
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (viewModel.isLoading.value) {
            ShimmerTextField()
            Spacer(modifier = Modifier.height(10.dp))
            ShimmerTextField()
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            if (editable) {
                CustomTextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.onNameChanged(it) },
                    label = "Имя"
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    value = viewModel.surname.value,
                    onValueChange = { viewModel.onSurnameChanged(it) },
                    label = "Фамилия"
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                DisabledTextField(viewModel.name.value)
                Spacer(modifier = Modifier.height(10.dp))
                DisabledTextField(viewModel.surname.value)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Text(
            text = "Выбранные конфигурации набора данных будут отображаться при просмотре общей информации о книге",
            fontSize = 12.sp,
            color = Color(0xFF272727),
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
        Spacer(modifier = Modifier.height(14.dp))
        viewModel.configurations.forEach { pair ->
            if (viewModel.isLoading.value) {
                ShimmerBox(title = pair.key)
            } else {
                CustomCheckBox(
                    value = pair.value.value,
                    onValueChange = {
                        if (viewModel.isEditable.value) {
                            viewModel.onCheckBoxStateChange(it)
                        }
                    },
                    title = pair.key
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (editable) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { viewModel.saveData() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF272727)
                ),
                modifier = Modifier
                    .padding(horizontal = 22.dp, vertical = 8.dp)
            ) {
                Text(text = "Сохранить изменения", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CustomCheckBox(
    value: Boolean,
    onValueChange: (String) -> Unit,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .border(
                    width = 2.dp,
                    color = if (value) Color.Transparent else Color(0xFFDED2A9),
                    shape = RoundedCornerShape(7.dp)
                )
                .clip(RoundedCornerShape(7.dp))
                .background(color = if (value) Color(0xFFFCE181) else Color.Transparent)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onValueChange(title) }
        ) {
            if (value) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "",
                    tint = Color(0xFF272727)
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 16.sp,
            color = Color(0xFF272727)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisabledTextField(
    value: String
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF272727),
            containerColor = Color(0xFFEFEBDE),
            disabledTextColor = Color(0xFF272727),
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color(0xFFDED2A9),
            focusedLabelColor = Color.Gray,
            unfocusedTrailingIconColor = Color.Transparent,
            focusedTrailingIconColor = Color(0xFFDED2A9),
            focusedIndicatorColor = Color(0xFFDED2A9),
            unfocusedSupportingTextColor = Color.LightGray,
            errorIndicatorColor = Color.Red
        ),
        trailingIcon = {
            if (value.isNotBlank()) {
                Icon(
                    painterResource(id = R.drawable.close_circle),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onValueChange("") }
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
    )
}

@Composable
fun ShimmerBox(
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(7.dp))
                .shimmerEffect()
        ) { }
        Text(
            text = title,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 16.sp,
            color = Color(0xFF272727)
        )
    }

}

@Composable
fun ShimmerTextField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEFEBDE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmerEffect()
        ) {}
    }
}