package com.timkom.carpaw.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun CreateRideScreen(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )  {
        Column(
            modifier = Modifier
                .offset(
                    x = 16.dp,
                    y = 0.dp
                )
                .padding(vertical = 10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Box(
                modifier = Modifier
                    .requiredWidth(width = 328.dp)
                    .requiredHeight(height = 31.dp)
            ) {
                Text(
                    text = "Create a Ride",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 1.5.em,
                    ),
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(
                            x = 0.dp,
                            y = 2.dp
                        )
                        .requiredWidth(width = 328.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateRideScreen()
    }
}