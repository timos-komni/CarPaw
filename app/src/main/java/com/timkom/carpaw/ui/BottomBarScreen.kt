package com.timkom.carpaw.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Search : BottomBarScreen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    object CreateRide : BottomBarScreen(
        route = "createRide",
        title = "Create a Ride",
        icon = Icons.Default.Place
    )

    object MyRides : BottomBarScreen(
        route = "myRides",
        title = "My Rides",
        icon = Icons.Default.Favorite
    )

    object Profile : BottomBarScreen(
        route = "login",
        title = "Login",
        icon = Icons.Default.AccountCircle
    )

}