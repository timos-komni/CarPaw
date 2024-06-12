package com.timkom.carpaw.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.EmailTextField
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.PasswordTextField
import com.timkom.carpaw.ui.theme.CarPawTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier){
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            PageHeading(
                Modifier,
                title = R.string.create_account__title
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(value = "", onValueChange = {}, label = "Email")
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(value = "", onValueChange = {}, label = "Password")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /*TODO: Create account logic*/ }) {
                Text("Create Account")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateAccountScreen(onBackClick = {})
    }
}

