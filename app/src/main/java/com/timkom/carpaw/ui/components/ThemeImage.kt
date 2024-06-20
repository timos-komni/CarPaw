package com.timkom.carpaw.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

// TODO remove
@Composable
fun ThemedImage(
    modifier: Modifier = Modifier,
    lightImage: Painter,
    darkImage: Painter
) {
    val isDarkTheme = isSystemInDarkTheme()
    val image = if (isDarkTheme) darkImage else lightImage
    Image(painter = image, contentDescription = null, modifier = modifier)
}