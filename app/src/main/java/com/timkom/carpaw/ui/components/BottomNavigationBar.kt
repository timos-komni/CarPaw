package com.timkom.carpaw.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

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
        NavigationIcon(R.drawable.search, "Search", "Search")
        NavigationIcon(R.drawable.add_location, "Create a Ride", "Create a Ride")
        NavigationIcon(R.drawable.folder_data, "My Rides", "My Rides")
        NavigationIcon(R.drawable.account_circle, "Login", "Login")
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    CarPawTheme {
        BottomNavigationBar()
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
