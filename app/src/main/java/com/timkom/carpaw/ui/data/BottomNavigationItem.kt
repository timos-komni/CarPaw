package com.timkom.carpaw.ui.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.timkom.carpaw.R
import com.timkom.carpaw.util.Either

sealed class BottomNavigationItem(
    val route: String,
    @StringRes val title: Int,
    val icon: Either<ImageVector, Int>
) {

    data object Home : BottomNavigationItem(
        route = "home",
        title = R.string.bottom_navigation_item__home__title,
        icon = Either.Left(Icons.Default.Home)
    )

    data object Search : BottomNavigationItem(
        route = "search",
        title = R.string.bottom_navigation_item__search__title,
        icon = Either.Left(Icons.Default.Search)
    )

    data object CreateRide : BottomNavigationItem(
        route = "createRide",
        title = R.string.bottom_navigation_item__create_ride__title,
        icon = Either.Right(R.drawable.add_location)
    )

    data object MyRides : BottomNavigationItem(
        route = "myRides",
        title = R.string.bottom_navigation_item__my_rides__title,
        icon = Either.Right(R.drawable.folder_data)
    )

}