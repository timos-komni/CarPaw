package com.timkom.carpaw.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.Either

@Composable
fun ElevatedIconButton(
    @StringRes title: Int,
    icon: Either<ImageVector, Int>,
    modifier: Modifier = Modifier,
    onClick: ()-> Unit
) {
    ElevatedButton(
        contentPadding = PaddingValues(horizontal =  8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier.requiredWidth(165.dp),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when(icon) {
                is Either.Left -> Icon(
                    imageVector = icon.value,
                    contentDescription = null,
                    modifier = modifier.requiredSize(size = 22.dp)
                )
                is Either.Right -> Icon(
                    painter = painterResource(id = icon.value),
                    contentDescription = null,
                    modifier = modifier.requiredSize(size = 22.dp)
                )
            }
            Text(
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun IconButtonPreview() {
    CarPawTheme(dynamicColor = false){
        ElevatedIconButton(
            title = R.string.create_ride__title,
            icon =  Either.Right(R.drawable.add_location)
        ) {

        }
    }

}