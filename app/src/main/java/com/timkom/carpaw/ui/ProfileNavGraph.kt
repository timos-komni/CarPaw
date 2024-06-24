package com.timkom.carpaw.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.screens.ProfileScreen
import com.timkom.carpaw.ui.viewmodels.FullScreenDialogViewModel
import com.timkom.carpaw.ui.viewmodels.MainViewModel

@Composable
fun ProfileNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    dialogViewModel: FullScreenDialogViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
    onLogoutClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "profile",
        modifier = modifier
    ) {
        composable(route = "profile") {
            dialogViewModel.setAll(title = stringResource(R.string.profile__title),
                onBackButton = { navController.popBackStack() }
            )
            ProfileScreen(onLogoutClick = { //TODO
                //mainViewModel.userIsConnected.value = false
              //  dialogViewModel.shouldDismiss.value = true
              //  onLogoutClick()
            })
        }
    }
}