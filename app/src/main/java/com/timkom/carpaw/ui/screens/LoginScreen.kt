package com.timkom.carpaw.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.EmailTextField
import com.timkom.carpaw.ui.components.PasswordTextField
import com.timkom.carpaw.ui.components.ThemedImage
import com.timkom.carpaw.ui.components.buttons.ColoredButton
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val username by viewModel.username
    val password by viewModel.password
    val loginStatus by viewModel.loginStatus
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
       Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
       ) {
           ThemedImage(
               lightImage = painterResource(id = R.drawable.city_driver_decor_foreground),
               darkImage = painterResource(id = R.drawable.city_driver_dark_decor_foreground),
               modifier = Modifier.size(300.dp)
           )
            Column(
                modifier = Modifier
                   // .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmailTextField(
                    value = username,
                    onValueChange = viewModel::onUsernameChange,
                    placeholder = stringResource(R.string.login_card__email_field__placeholder),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = stringResource(R.string.login_card__password_field__placeholder),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.login_card__forgot_password_ling__text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.outfit)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.25.sp,
                        textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End)
                        .clickable {onForgotPasswordClick()}
                )
                Spacer(modifier = Modifier.height(16.dp))
                ColoredButton(
                    title = R.string.login_card__login_button__text,
                    onClick = {
                        if (username.isNotBlank() && password.isNotBlank()) {
                            viewModel.login()
                            coroutineScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        if (loginStatus) "Login successful" else "Login failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please, fill the email and password fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.login_card__no_account_prompt__text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.outfit)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555252),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.25.sp,
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 2.dp
                        )
                )
                Text(
                    text = stringResource(R.string.login_card__create_account__text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.outfit)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.25.sp,
                        //textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 0.dp,
                            bottom = 8.dp
                        )
                        .clickable { onCreateAccountClick() }
                )
            }
       }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        LoginScreen(onCreateAccountClick = {},  onForgotPasswordClick = {})
    }
}
