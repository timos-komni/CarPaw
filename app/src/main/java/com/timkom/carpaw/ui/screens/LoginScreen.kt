package com.timkom.carpaw.ui.screens

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.timkom.carpaw.GlobalData
import com.timkom.carpaw.R
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.CustomAlertDialog
import com.timkom.carpaw.ui.components.EmailTextField
import com.timkom.carpaw.ui.components.PasswordTextField
import com.timkom.carpaw.ui.components.buttons.CustomButton
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.LoginViewModel
import com.timkom.carpaw.util.checkIfAnyBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onUserLogin: () -> Unit
) {
    val username by viewModel.username
    val password by viewModel.password
    val loginStatus by viewModel.loginStatus
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogMessage by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TODO (Chloe) Γιατί LazyColumn και όχι Column?!
        LazyColumn(
            modifier = Modifier.padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.city_driver_decor_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
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
                            .clickable { onForgotPasswordClick() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        title = R.string.login_card__login_button__text,
                        onClick = {
                            if (username.isNotBlank() && password.isNotBlank()) {
                                coroutineScope.launch {
                                    val user = viewModel.login().await() // wait for the login coroutine
                                    val userIsConfirmed = user != null && loginStatus
                                    val token = if (userIsConfirmed) SupabaseManager.getRefreshToken() else null
                                    GlobalData.user = if (userIsConfirmed) user else null
                                    withContext(Dispatchers.Main) {
                                        token?.let {
                                            context.getSharedPreferences(
                                                context.getString(R.string.preferences_file),
                                                Context.MODE_PRIVATE
                                            ).edit()
                                                .putString(
                                                    context.getString(R.string.supabase_refresh_token_pref_key),
                                                    token
                                                )
                                                .apply()
                                        }
                                        dialogMessage = if (userIsConfirmed) {
                                            onUserLogin.invoke()
                                            // TODO (Chloe) Δεν θα δουλέψει σε αυτήν την περίπτωση, από την άποψη ότι θα έχει κλείσει ήδη το παράθυρο
                                            "Welcome back, ${user!!.firstName} ${user.lastName}!"
                                        } else if (user != null) {
                                            "Please check your email to confirm your account before login!"
                                        } else {
                                            "Login failed. Please check your username and password."
                                        }
                                        showDialog = true
                                    }
                                }
                            }
                        },
                        enabled = username.isNotBlank() && password.isNotBlank()
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
    if (showDialog) {
        CustomAlertDialog(
            message = dialogMessage,
            onDismissRequest = { showDialog = false },
            onConfirm = {
                showDialog = false
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        LoginScreen(onCreateAccountClick = {}, onForgotPasswordClick = {}, onUserLogin = {})
    }
}
