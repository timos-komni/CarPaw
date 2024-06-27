package com.timkom.carpaw.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.Either

/**
 * A custom elevated button with an icon.
 * @param title The title of the button.
 * @param icon The icon to display.
 * @param onClick The on click listener.
 * @param enabled Whether the button is enabled or not.
 * @param modifier The modifier.
 */
@Composable
fun ElevatedIconButton(
    @StringRes title: Int,
    icon: Either<ImageVector, Int>,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    ElevatedButton(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = if (isPortrait) {
            Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        } else {
            Modifier.width(400.dp).padding(horizontal = 32.dp)
        },
        onClick = onClick,
        enabled = enabled
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
                style = MaterialTheme.typography.bodyMedium,
                fontWeight =  FontWeight.Medium,
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
            icon =  Either.Right(R.drawable.add_location),
            onClick = {},
            enabled = true
        )
    }
}