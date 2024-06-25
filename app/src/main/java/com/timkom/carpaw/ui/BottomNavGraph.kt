package com.timkom.carpaw.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.SearchResultCardData
import com.timkom.carpaw.ui.data.BottomNavigationItem
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.screens.AvailableRidesScreen
import com.timkom.carpaw.ui.screens.CreateAccountScreen
import com.timkom.carpaw.ui.screens.CreateRideScreen
import com.timkom.carpaw.ui.screens.ForgotPasswordScreen
import com.timkom.carpaw.ui.screens.HomeScreen
import com.timkom.carpaw.ui.screens.MyRidesScreen
import com.timkom.carpaw.ui.screens.PreLoginCreateRideScreen
import com.timkom.carpaw.ui.screens.PreLoginMyRidesScreen
import com.timkom.carpaw.ui.screens.RideDetailsScreen
import com.timkom.carpaw.ui.screens.SearchScreen
import com.timkom.carpaw.ui.viewmodels.MainViewModel
import com.timkom.carpaw.ui.viewmodels.SearchRideViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
    searchRideViewModel: SearchRideViewModel = viewModel()
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
            HomeScreen(
                onCreateARideClick = {
                    navController.navigate(BottomNavigationItem.CreateRide.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    // TODO Κακή λύση, αλλά δεν μπόρεσα να σκεφτώ κάτι άλλο
                    mainViewModel.navigationSelectedItem.intValue = 2
                },
                onSearchForARideClick = {
                    navController.navigate(BottomNavigationItem.CreateRide.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    // TODO Κακή λύση, αλλά δεν μπόρεσα να σκεφτώ κάτι άλλο
                    mainViewModel.navigationSelectedItem.intValue = 3
                }
            )
        }
        composable(route = BottomNavigationItem.CreateRide.route) {
            mainViewModel.setAll(stringResource(R.string.create_ride__title))
            if (mainViewModel.userIsConnected.value) {
                CreateRideScreen(onNavigateToMyRides = {
                    navController.navigate(BottomNavigationItem.MyRides.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    mainViewModel.navigationSelectedItem.intValue = 3
                })
            } else {
                PreLoginCreateRideScreen(mainViewModel = mainViewModel)
            }
        }
        composable(route = BottomNavigationItem.Search.route) {
            mainViewModel.setAll(stringResource(R.string.search_ride__title))
            SearchScreen(onSearchClick = { startLocation, destinationLocation, date, animals ->
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("start_location", startLocation)
                    set("destination_location", destinationLocation)
                    set("date", date)
                    set("selected_animals", animals)
                }
                navController.navigate("available_rides")
            })
        }
        composable(route = "available_rides") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.available_rides__title),
                onBackButton = { navController.popBackStack() }
            )
            AvailableRidesScreen(
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("start_location"),
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("destination_location"),
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("date"),
                navController.previousBackStackEntry?.savedStateHandle?.get<List<CompanionAnimalItem>>("selected_animals"),
                onViewRideDetailsClick = { data ->
                    //searchRideViewModel.setSelectedRide(ride)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_data", data)
                    navController.navigate("ride_details")
                }
            )
        }
        composable(route = "ride_details") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.ride_details__title),
                onBackButton = { navController.popBackStack() }
            )
            RideDetailsScreen(
                data = navController.previousBackStackEntry?.savedStateHandle?.get<SearchResultCardData>("selected_data"),
                //viewModel = searchRideViewModel,
            )
        }
        composable(route = BottomNavigationItem.MyRides.route) {
            mainViewModel.setAll(stringResource(R.string.my_rides__title))
            if (mainViewModel.userIsConnected.value) {
                MyRidesScreen()
            } else {
                PreLoginMyRidesScreen(mainViewModel = mainViewModel)
            }
        }
        composable(route = "create_account") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.create_account__title),
                onBackButton = { navController.popBackStack() }
            )
            CreateAccountScreen()
        }
        composable(route = "forgot_password") {
            mainViewModel.setAll(
                screenTitle = stringResource(R.string.forgot_password__title),
                onBackButton = { navController.popBackStack() }
            )
            ForgotPasswordScreen()
        }

    }
}