package com.timkom.carpaw.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun ProfileOption(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileOptionPreview() {
    CarPawTheme(dynamicColor = false){
        ProfileOption(
            title = "Account settings",
            description = "Notifications, passwords and more",
            onClick = { /* Handle click */ }
        )
    }

}
