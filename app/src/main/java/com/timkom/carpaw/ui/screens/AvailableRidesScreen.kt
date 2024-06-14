package com.timkom.carpaw.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.buttons.ArrowBackButton
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun AvailableRidesScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier){
            ArrowBackButton(onBackClick = onBackClick)
            PageHeading(
                Modifier,
                title = R.string.available_rides__title
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
             }
    }
}

@Preview(showBackground = true)
@Composable
fun AvailableRidesScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        AvailableRidesScreen()
    }
}

