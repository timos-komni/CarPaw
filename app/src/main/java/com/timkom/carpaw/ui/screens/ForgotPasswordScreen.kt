package com.timkom.carpaw.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.timkom.carpaw.ui.theme.CarPawTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    // TODO remove parameter
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(value = "", onValueChange = {}, label = "Email")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /*TODO:  logic*/ }) {
                Text("Test")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        ForgotPasswordScreen(onBackClick = {})
    }
}

