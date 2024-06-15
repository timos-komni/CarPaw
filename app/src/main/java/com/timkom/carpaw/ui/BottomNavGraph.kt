package com.timkom.carpaw.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.screens.AvailableRidesScreen
import com.timkom.carpaw.ui.screens.CreateAccountScreen
import com.timkom.carpaw.ui.screens.CreateRideScreen
import com.timkom.carpaw.ui.screens.ForgotPasswordScreen
import com.timkom.carpaw.ui.screens.HomeScreen
import com.timkom.carpaw.ui.screens.LoginScreen
import com.timkom.carpaw.ui.screens.MyRidesScreen
import com.timkom.carpaw.ui.screens.SearchScreen
import com.timkom.carpaw.ui.viewmodels.MainViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavigationItem.Home.route) {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.app_name)
            )
            HomeScreen()
        }
        composable(route = BottomNavigationItem.CreateRide.route) {
            mainViewModel.setAll(stringResource(R.string.create_ride__title))
            CreateRideScreen()
        }
        composable(route = BottomNavigationItem.Search.route) {
            mainViewModel.setAll(stringResource(R.string.search_ride__title))
            SearchScreen(onSearchClick = {
                navController.navigate("available_rides")
            })
        }
        composable(route = "available_rides") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.available_rides__title),
                onBackButton = { navController.popBackStack() }
            )
            AvailableRidesScreen(/*onBackClick = { navController.popBackStack() }*/)
        }
        composable(route = BottomNavigationItem.MyRides.route) {
            mainViewModel.setAll(stringResource(R.string.my_rides__title))
            MyRidesScreen()
        }
        composable(route = BottomNavigationItem.Profile.route) {
            mainViewModel.setAll("Login")
            LoginScreen(onCreateAccountClick = {
                navController.navigate("create_account")
            }, onForgotPasswordClick = {
                navController.navigate("forgot_password")
            })
        }
        composable(route = "create_account") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.create_account__title),
                onBackButton = { navController.popBackStack() }
            )
            CreateAccountScreen(/*TODO remove*//*onBackClick = { navController.popBackStack() }*/)
        }
        composable(route = "forgot_password") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.forgot_password__title),
                onBackButton = { navController.popBackStack() }
            )
            ForgotPasswordScreen(/*TODO remove*//*onBackClick = {
                navController.popBackStack()
            }*/)
        }

    }
}