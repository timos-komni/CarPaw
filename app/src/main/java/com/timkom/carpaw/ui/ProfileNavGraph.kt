package com.timkom.carpaw.ui


import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkom.carpaw.GlobalData
import com.timkom.carpaw.R
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.screens.ProfileScreen
import com.timkom.carpaw.ui.viewmodels.FullScreenDialogViewModel
import com.timkom.carpaw.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    dialogViewModel: FullScreenDialogViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = "profile",
        modifier = modifier
    ) {
        composable(route = "profile") {
            dialogViewModel.setAll(title = stringResource(R.string.profile__title))
            ProfileScreen(onLogoutClick = {
                coroutineScope.launch {
                    if (SupabaseManager.logoutUser()) {
                        mainViewModel.userIsConnected.value = false
                        dialogViewModel.shouldDismiss.value = true
                        GlobalData.user = null
                        withContext(Dispatchers.Main) {
                            context.run {
                                getSharedPreferences(
                                    getString(R.string.preferences_file),
                                    Context.MODE_PRIVATE
                                ).edit()
                                    .putString(
                                        getString(R.string.supabase_access_token_pref_key),
                                        ""
                                    )
                                    .putString(
                                        getString(R.string.supabase_refresh_token_pref_key),
                                        ""
                                    )
                                    .apply()
                            }
                        }
                    } else {
                        // TODO
                    }
                }
            })
        }
    }
}