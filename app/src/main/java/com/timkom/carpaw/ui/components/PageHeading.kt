package com.timkom.carpaw.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun PageHeading(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 36.dp)
            .padding(4.dp)
    ) {
        Text(
            text = stringResource(id = title),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            lineHeight = 1.5.em,
            modifier = Modifier
                .offset(
                    x = 0.dp,
                    y = 4.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Preview(widthDp = 328, heightDp = 31)
@Composable
private fun PageHeadingPreview() {
    CarPawTheme(dynamicColor = false){
        PageHeading(
            Modifier,
            title = R.string.create_ride__title
        )
    }

}