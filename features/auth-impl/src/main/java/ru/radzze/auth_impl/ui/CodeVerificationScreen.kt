package ru.radzze.auth_impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ru.radzze.auth_impl.R


@Composable
fun CodeVerificationScreen(
    email: String,
    onNavigateToSignIn: () -> Unit,
    onNavigateToScanFeature: () -> Unit,
    viewModel: CodeVerificationViewModel = hiltViewModel()
) {
    val timer by viewModel.timer.collectAsState()
    val isOver by viewModel.isOver.collectAsState()
    val isValid by viewModel.isValid.collectAsState()
    val blockTimer by viewModel.blockTimer.collectAsState()
    val isUnblocked by viewModel.isUnblocked.collectAsState()
    val textList = viewModel.textList

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 2.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Leading Icon",
                    modifier = Modifier
                        .size(42.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { onNavigateToSignIn() }
                )
                Text(
                    text = "Код подтверждения",
                    modifier = Modifier
                        .padding(start = 2.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "На электронную почту $email было отправлено письмо с кодом подтверждения",
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(20.dp))
            VerificationCodeFields(
                isValid = isValid,
                viewModel = viewModel,
                textList = textList,
                isUnblocked = isUnblocked
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (isValid == false || !isUnblocked) {
                val text = if (!isUnblocked) "Слишком много попыток" else "Код введён не верно"
                Text(
                    text = text,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            if (!isUnblocked) {
                Text(
                    text = "Повторно отправить код через: 0${blockTimer / 60}:${if (blockTimer % 60 >= 10) "${blockTimer % 60}" else "0${blockTimer % 60}"}",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            } else {
                if (isOver) {
                    Text(
                        text = "Повторно отправить код",
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { viewModel.resendCode() },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Blue,
                    )
                } else {
                    Text(
                        text = "Повторно отправить код через: 00:${if (timer >= 10) "$timer" else "0$timer"}",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
        Button(
            onClick = {
                viewModel.saveAuthState()
                onNavigateToScanFeature()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp, vertical = 48.dp),
            shape = RoundedCornerShape(50.dp),
            enabled = (isValid == true),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                disabledContainerColor = Color(239, 235, 222),
                disabledContentColor = Color(140, 140, 140)
            ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = "Продолжить", fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun InputField(
    value: TextFieldValue,
    borderColor: Color,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    isUnblocked: Boolean
) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .background(Color(239, 235, 222))
            .focusRequester(focusRequester),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color(0xFF272727)),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = null
        ),
        enabled = isUnblocked
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun VerificationCodeFields(
    isValid: Boolean?,
    viewModel: CodeVerificationViewModel,
    textList: List<TextFieldValue>,
    isUnblocked: Boolean
) {
    val borderColor =
        if (isValid == true) Color.Green else if (isValid == null) Color.Transparent else Color.Red
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(300)
        viewModel.requestList[0].requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in textList.indices) {
            InputField(
                value = textList[i],
                borderColor = borderColor,
                onValueChange = { newValue ->
                    if (textList[i].text.isNotBlank()) {
                        if (newValue.text == "") {
                            viewModel.changeTextListItem(
                                i,
                                TextFieldValue(
                                    text = "",
                                    selection = TextRange(0)
                                )
                            )
                        }
                        return@InputField
                    }
                    viewModel.changeTextListItem(
                        i,
                        TextFieldValue(
                            text = newValue.text,
                            selection = TextRange(newValue.text.length)
                        )
                    )
                    viewModel.connectInputtedCode {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (!it) {
                            for (text in textList.indices) {
                                val value = TextFieldValue(
                                    text = "",
                                    selection = TextRange(0)
                                )
                                viewModel.changeTextListItem(text, value)
                            }
                        }
                    }
                    viewModel.nextFocus()
                },
                focusRequester = viewModel.requestList[i],
                isUnblocked = isUnblocked
            )
            if (i != textList.size - 1) {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}