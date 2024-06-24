package com.timkom.carpaw.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.GlobalData
import com.timkom.carpaw.ui.components.ProfileOption
import com.timkom.carpaw.ui.components.UserImage
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun ProfileScreen(onLogoutClick: () -> Unit) {
    val user = GlobalData.user
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        user?.let {
            UserImage(color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${it.firstName} ${it.lastName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "View and edit profile >",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest)
            Spacer(modifier = Modifier.height(8.dp))

            ProfileOption(
                title = "Account settings",
                description = "Notifications, passwords and more",
                onClick = { /* Handle Account settings click */ }
            )
            ProfileOption(
                title = "Payments",
                description = "Payment methods, bank details and more",
                onClick = { /* Handle Payments click */ }
            )
            ProfileOption(
                title = "Ride statistics",
                description = "Ratings, reviews and more",
                onClick = { /* Handle Ride statistics click */ }
            )
            ProfileOption(
                title = "Accessibility",
                description = "Language, text size and more",
                onClick = { /* Handle Accessibility click */ }
            )

            Spacer(modifier = Modifier.height(16.dp))
           // Divider(color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Log out",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { onLogoutClick() }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileContentPreview() {
    CarPawTheme(dynamicColor = false) {
        ProfileScreen(onLogoutClick = {})
    }
}
