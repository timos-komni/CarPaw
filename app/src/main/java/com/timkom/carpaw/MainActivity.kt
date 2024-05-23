package com.timkom.carpaw

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CarPawTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("CarPaw") },
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        LoginCard()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarPawTheme {
        Greeting("Android")
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
                    text = "Welcome to your CarPaw account",
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
                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    placeholder = {
                        Text(text = "Email or phone number")
                    },
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(text = "Password")
                    },
                    modifier = modifier
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "Forgot Password?",
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
                )
            }
            Button(
                onClick = {  },
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
                    text = "Login",
                    modifier = modifier
                        .padding(horizontal = 20.dp)
                )
            }
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
                    text = "Cancel",
                    modifier = modifier
                        .padding(horizontal = 20.dp)
                )
            }
            Text(
                text = "Does not have an account yet?",
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
                text = "Create Account",
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
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 0.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )
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