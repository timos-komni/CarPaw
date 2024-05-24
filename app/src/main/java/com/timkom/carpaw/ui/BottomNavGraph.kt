package com.timkom.carpaw.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.ui.screens.CreateRideScreen
import com.timkom.carpaw.ui.screens.HomeScreen
import com.timkom.carpaw.ui.screens.LoginScreen
import com.timkom.carpaw.ui.screens.MyRidesScreen
import com.timkom.carpaw.ui.screens.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavigationItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavigationItem.CreateRide.route) {
            CreateRideScreen()
        }
        composable(route = BottomNavigationItem.Search.route) {
            SearchScreen()
        }
        composable(route = BottomNavigationItem.MyRides.route) {
            MyRidesScreen()
        }
        composable(route = BottomNavigationItem.Profile.route) {
            LoginScreen()
        }
    }
}