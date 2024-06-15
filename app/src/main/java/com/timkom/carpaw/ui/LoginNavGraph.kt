package com.timkom.carpaw.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.screens.CreateAccountScreen
import com.timkom.carpaw.ui.screens.ForgotPasswordScreen
import com.timkom.carpaw.ui.screens.LoginScreen
import com.timkom.carpaw.ui.viewmodels.FullScreenDialogViewModel

@Composable
fun LoginNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    dialogViewModel: FullScreenDialogViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable(route = "login") {
            dialogViewModel.setAll("Login")
            LoginScreen(onCreateAccountClick = {
                navController.navigate("create_account")
            }, onForgotPasswordClick = {
                navController.navigate("forgot_password")
            })
        }
        composable(route = "create_account") {
            dialogViewModel.setAll(
                title = stringResource(R.string.create_account__title),
                onBackButton = { navController.popBackStack() }
            )
            CreateAccountScreen()
        }
        composable(route = "forgot_password") {
            dialogViewModel.setAll(
                title = stringResource(R.string.forgot_password__title),
                onBackButton = { navController.popBackStack() }
            )
            ForgotPasswordScreen()
        }
    }
}