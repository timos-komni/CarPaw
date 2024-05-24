package com.timkom.carpaw.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.ui.screens.HomeScreen
import com.timkom.carpaw.ui.screens.LoginScreen
import com.timkom.carpaw.ui.screens.SearchScreen
import com.timkom.carpaw.ui.screens.MyRidesScreen
import com.timkom.carpaw.ui.screens.CreateRideScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            LoginScreen()
        }
        composable(route = BottomBarScreen.CreateRide.route) {
            CreateRideScreen()
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.MyRides.route) {
            MyRidesScreen()
        }
    }
}