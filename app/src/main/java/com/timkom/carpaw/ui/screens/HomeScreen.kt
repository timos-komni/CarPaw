package com.timkom.carpaw.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(height = 210.dp)
                .background(color = Color(0xffd2ebe4))
                .padding(horizontal = 20.dp)
        ){
            Image(
                painter = painterResource(id = R.mipmap.ic_hero_foreground),
                contentDescription = "Decorative image",
                modifier = Modifier
                    .requiredWidth(width = 266.dp)
                    .requiredHeight(height = 149.dp))
            Text(
                text = "Find a low cost ride for your companion animal",
                color = Color(0xff006b5f),
                textAlign = TextAlign.Center,
                lineHeight = 1.4.em,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.15.sp),
                modifier = Modifier
                    .requiredWidth(width = 322.dp)
                    .requiredHeight(height = 56.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        HomeScreen()
    }
}
