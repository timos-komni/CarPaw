package com.timkom.carpaw.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .background(color = Color(0xffd2ebe4))
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        NavigationIcon(R.drawable.searchicon, "Search", "Search")
        //NavigationIcon(R.drawable.add_location, "Create a Ride", "Create a Ride")
        //NavigationIcon(R.drawable.folder_data, "My Rides", "My Rides")
        //NavigationIcon(R.drawable.account_circle, "Login", "Login")
    }
}

@Composable
fun NavigationIcon(iconRes: Int, contentDescription: String, text: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(Color(0xff1c1b1f)),
            modifier = Modifier.requiredSize(24.dp)
        )
        Text(
            text = text,
            color = Color.Black,
            lineHeight = 1.33.em,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
        )
    }
}
