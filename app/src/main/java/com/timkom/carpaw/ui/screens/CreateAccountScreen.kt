package com.timkom.carpaw.ui.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.components.EmailTextField
import com.timkom.carpaw.ui.components.GenericTextField
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.PasswordTextField
import com.timkom.carpaw.ui.components.buttons.CustomButton
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.checkIfAnyBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("SuspiciousIndentation")
@Composable
fun CreateAccountScreen(
    onAccountCreation: () -> Unit = {}
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var firstName by rememberSaveable {
        mutableStateOf("")
    }
    var lastName by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val passwordCheck = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex()


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(40.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(value = email, onValueChange = { email = it }, label = "Email")
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !passwordCheck.matches(it) && it.isNotEmpty()
                },
                label = "Password",
                isError = passwordError,
                errorText = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long"
            )
            Spacer(modifier = Modifier.height(20.dp))
            GenericTextField(value = firstName, onValueChange = { firstName = it }, label = "First Name")
            Spacer(modifier = Modifier.height(20.dp))
            GenericTextField(value = lastName, onValueChange = { lastName = it }, label = "Last Name")
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(
                title = R.string.create_account__button,
                onClick = {
                    coroutineScope.launch {
                        val result = SupabaseManager.createUser(
                            email = email,
                            password = password,
                            firstName = firstName,
                            lastName = lastName
                        )
                        if (result.first) {
                            withContext(Dispatchers.Main) {
                                AlertDialog.Builder(context)
                                    .setCancelable(false)
                                    .setMessage("Your account has been created successfully! Please check your email to confirm your account.")
                                    .setNeutralButton("OK") { dialog, _ ->
                                        onAccountCreation.invoke()
                                        dialog.dismiss()
                                    }
                                    .create().show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                AlertDialog.Builder(context)
                                    .setCancelable(false)
                                    .setMessage("Account could not be created.")
                                    .setNeutralButton("OK") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    .create().show()
                            }
                        }
                        Log.d("@CreateAccount", result.second.toString())
                    }
                },
                enabled = !checkIfAnyBlank(email, password, firstName, lastName) && !passwordError
            )
        }
    }


@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateAccountScreen()
    }
}

