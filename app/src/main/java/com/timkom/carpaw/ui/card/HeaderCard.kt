package com.timkom.carpaw.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.screens.HomeScreen
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun HeaderCard(
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 200.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .requiredHeight(height = 210.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_hero_foreground),
                contentDescription = "Decorative image",
                modifier = Modifier
                    .requiredWidth(width = 266.dp)
                    .requiredHeight(height = 140.dp))
            Text(
                text = "Find a low cost ride for your companion animal",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
fun HeaderCardPreview() {
    CarPawTheme(dynamicColor = false) {
        HeaderCard()
    }
}
