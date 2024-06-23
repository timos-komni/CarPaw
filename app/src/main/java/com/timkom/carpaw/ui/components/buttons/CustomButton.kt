package com.timkom.carpaw.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomButton(
    @StringRes title: Int,
    onClick: () -> Unit,
    enabled: Boolean
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (enabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest,
        contentColor = if (enabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    )
    //detect the current orientation
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    Button(
        onClick = onClick,
        modifier = if (isPortrait) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.width(300.dp)
        },
        shape = RoundedCornerShape(10.dp),
        colors = colors,
        enabled = enabled
    ) {
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight =  FontWeight.Medium
            )
    }
}