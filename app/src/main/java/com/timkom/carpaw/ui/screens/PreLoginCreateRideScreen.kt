package com.timkom.carpaw.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.ThemedImage
import com.timkom.carpaw.ui.components.buttons.ColoredButton
import androidx.navigation.NavController
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.MainViewModel

@Composable
fun PreLoginCreateRideScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ThemedImage(
            lightImage = painterResource(id = R.drawable.person_dog_phone_decor_foreground),
            darkImage = painterResource(id = R.drawable.person_dog_phone_dark_decor_foreground),
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(id = R.string.login__create_ride_text),
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 1.4.em,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                letterSpacing = 0.15.sp),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.create_ride_descr__text),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 1.4.em,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                letterSpacing = 0.15.sp),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        ColoredButton(title = R.string.login__button) {
            mainViewModel.toggleLoginDialog(true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreLoginCreateRideScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        PreLoginCreateRideScreen()
    }
}