package com.timkom.carpaw.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArrowBackButton(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onBackClick, modifier = modifier) {
        Icon(
            Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
    }
}