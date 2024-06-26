package com.timkom.carpaw.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.timkom.carpaw.R
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.SimpleFullScreenDialog
import com.timkom.carpaw.ui.components.EmailTextField
import com.timkom.carpaw.ui.components.PasswordTextField
import com.timkom.carpaw.ui.theme.CarPawTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreenOld(modifier: Modifier = Modifier) {
    LoginCard(modifier)
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreviewOld() {
    CarPawTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            LoginScreenOld()
        }

    }
}

@Composable
fun LoginCard(modifier: Modifier = Modifier) {
    var user by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var showForgotPwDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showCreateAccDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Card(
        Modifier
            .shadow(
                elevation = 12.dp,
                spotColor = Color(0x54000000),
                ambientColor = Color(0x54000000)
            )
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp)),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .height(48.dp)
                    .background(color = MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_card__title__text),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.outfit)),
                        fontWeight = FontWeight(500),
                        color = Color.White,
                    ),
                    modifier = modifier
                        .padding(
                            vertical = 11.dp,
                            horizontal = 34.dp
                        )
                )
            }
            Column(
                modifier = modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmailTextField(
                    value = user,
                    onValueChange = { user = it },
                    placeholder = stringResource(R.string.login_card__email_field__placeholder),
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.login_card__password_field__placeholder),
                    modifier = modifier
                        .padding(vertical = 8.dp)
                )
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
                    modifier = modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End)
                        .clickable { }
                )
            }
            Button(
                onClick = {
                    if (user.isNotBlank() and password.isNotBlank()) {
                        coroutineScope.launch {
                            val users = SupabaseManager.getUsersByEmail(user, password, false)
                            if (users.isNotEmpty() && users.size == 1) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Welcome ${users[0].firstName} ${users[0].lastName}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "User not found",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
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
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.login_card__login_button__text),
                    modifier = modifier
                        .padding(horizontal = 20.dp)
                )
            }
            // TODO (Chloe & Me) We should probably remove the Cancel button..it doesn't make sense
            OutlinedButton(
                onClick = {  },
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.login_card__cancel_button__text),
                    modifier = modifier
                        .padding(horizontal = 20.dp)
                )
            }
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
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 2.dp
                    )
                    .align(Alignment.CenterHorizontally)
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
                    //extDecoration = TextDecoration.Underline,
                ),
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 0.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterHorizontally)
                    .clickable { showCreateAccDialog = true }
            )
        }
    }

    if (showCreateAccDialog) {
        SimpleFullScreenDialog(
            onDismissRequest = { showCreateAccDialog = false },
            title = "Create Account",
            actions = {
                IconButton(onClick = { /*TODO Check the fields and create account*/ }) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(value = "", onValueChange = {}, label = "Email")
                Spacer(modifier = Modifier.height(20.dp))
                PasswordTextField(value = "", onValueChange = {}, label = "Password")
                Spacer(modifier = Modifier.height(20.dp))
                // TODO ...
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginCardPreview() {
    CarPawTheme(dynamicColor = false) {
        LoginCard()
    }
}