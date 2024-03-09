package ru.radzze.auth_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.radzze.auth_impl.R


@Composable
fun AuthScreen(
    navigateToCodeVerification: (String) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 42.dp, end = 42.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "logo",
                modifier = Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            EmailField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                "E-mail"
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    navigateToCodeVerification(viewModel.email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                shape = RoundedCornerShape(50.dp),
                enabled = viewModel.enabledState,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    disabledContainerColor = Color(239, 235, 222),
                    disabledContentColor = Color(140, 140, 140)
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.get_code),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color.Gray,
            unfocusedTrailingIconColor = Color.Transparent,
            focusedTrailingIconColor = Color.LightGray,
            focusedIndicatorColor = Color.LightGray,
            unfocusedSupportingTextColor = Color.LightGray
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



