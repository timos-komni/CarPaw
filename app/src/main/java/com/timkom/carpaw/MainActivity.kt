package com.timkom.carpaw

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.BottomNavGraph
import com.timkom.carpaw.ui.FullScreenDialog
import com.timkom.carpaw.ui.LoginNavGraph
import com.timkom.carpaw.ui.ProfileNavGraph
import com.timkom.carpaw.ui.SimpleFullScreenDialog
import com.timkom.carpaw.ui.components.buttons.ArrowBackButton
import com.timkom.carpaw.ui.data.BottomNavigationItem
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.MainViewModel
import com.timkom.carpaw.util.Either
import com.timkom.carpaw.util.createTAGForKClass
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (GlobalData.user == null) {
                    connectUser()
                }
                Log.d(createTAGForKClass(MainActivity::class), GlobalData.user.toString())
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (GlobalData.user != null) {
                    SupabaseManager.refreshCurrentSession()
                }
            }
        }
        setContent {
            CarPawTheme(dynamicColor = false) {
                val mainViewModel: MainViewModel = viewModel()
                var navigationSelectedItem by mainViewModel.navigationSelectedItem
                val navController = rememberNavController()
                val screenTitle: String by mainViewModel.screenTitle
                val onBackButton: (() -> Unit)? by mainViewModel.onBackButton
                val actions: (@Composable () -> Unit)? by mainViewModel.actions
                val shouldHaveProfileAction: Boolean by mainViewModel.shouldHaveProfileAction
                var userIsConnected: Boolean by mainViewModel.userIsConnected
                var showLoginDialog : Boolean by mainViewModel.showLoginDialog
                var showProfileDialog : Boolean by mainViewModel.showProfileDialog

                //detect the current orientation
                val configuration = LocalConfiguration.current
                val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

                // if MainViewModel says that the user is not connected
                // check if the actual user instance is null
                if (!userIsConnected) {
                    userIsConnected = GlobalData.user != null
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        //if (isPortrait){
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = screenTitle,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors().copy(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    titleContentColor = MaterialTheme.colorScheme.primary
                                ),
                                navigationIcon = {
                                    onBackButton?.let {
                                        ArrowBackButton(onClick = it)
                                    }
                                },
                                actions = {
                                    actions?.invoke()
                                    // Should be the last action
                                    if (shouldHaveProfileAction) {
                                        if (!userIsConnected) {
                                            IconButton(
                                                onClick = { showLoginDialog = true },
                                                colors = IconButtonDefaults.iconButtonColors().copy(
                                                    contentColor = MaterialTheme.colorScheme.primary
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.AccountCircle,
                                                    contentDescription = "Login",
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        } else {
                                            // TODO customize it -> user is connected
                                            IconButton(onClick = { showProfileDialog = true }) {
                                                Icon(
                                                    imageVector = Icons.Default.AccountCircle,
                                                    contentDescription = "Profile",
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        },
                    //},
                    bottomBar = {
                        val navItems = listOf(
                            BottomNavigationItem.Home,
                            BottomNavigationItem.Search,
                            BottomNavigationItem.CreateRide,
                            BottomNavigationItem.MyRides,
                        )
                        NavigationBar {
                            navItems.forEachIndexed { index, bottomNavigationItem ->
                                NavigationBarItem(
                                    selected = index == navigationSelectedItem,
                                    label = {
                                        Text(text = stringResource(bottomNavigationItem.title))
                                    },
                                    icon = {
                                        when(bottomNavigationItem.icon) {
                                            is Either.Left -> Icon(
                                                imageVector = bottomNavigationItem.icon.value,
                                                contentDescription = stringResource(
                                                    bottomNavigationItem.title
                                                )
                                            )
                                            is Either.Right -> Icon(
                                                painterResource(id = bottomNavigationItem.icon.value),
                                                contentDescription = stringResource(
                                                    bottomNavigationItem.title
                                                )
                                            )
                                        }
                                    },
                                    onClick = {
                                        navigationSelectedItem = index
                                        navController.navigate(bottomNavigationItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        BottomNavGraph(navController = navController)
                    }
                }

                if (showLoginDialog) {
                    FullScreenDialog(
                        viewModelKey = "LOGIN_DIALOG",
                        onDismissRequest = { showLoginDialog = false }
                    ) {
                        val loginNavController = rememberNavController()
                        LoginNavGraph(
                            navController = loginNavController,
                            dialogViewModel = viewModel(key = "LOGIN_DIALOG")
                        )
                    }
                }
                
                if (showProfileDialog) {
                    FullScreenDialog(
                        viewModelKey = "PROFILE_DIALOG",
                        onDismissRequest = { showProfileDialog = false }
                    ) {
                        val profileNavController = rememberNavController()
                        ProfileNavGraph(
                            navController = profileNavController,
                            dialogViewModel = viewModel(key = "PROFILE_DIALOG"),
                            onLogoutClick = { //TODO
                               // showProfileDialog = false
                                //userIsConnected = false
                               // showLoginDialog = true
                            }
                        )

                    }
                }
            }
        }
    }


    private suspend fun connectUser() {
        val prefs = getSharedPreferences(
            getString(R.string.preferences_file),
            Context.MODE_PRIVATE
        )
        val accessTokenPrefKey = getString(R.string.supabase_access_token_pref_key)
        val refreshTokenPrefKey = getString(R.string.supabase_refresh_token_pref_key)
        val accessToken = prefs.getString(accessTokenPrefKey, "")!!
        val refreshToken = prefs.getString(refreshTokenPrefKey, "")!!
        val viewModel: MainViewModel by viewModels()

        val fetchUser: suspend (String) -> Unit = {
            val user = SupabaseManager.fetchUser(it)
            viewModel.userIsConnected.value = user?.let { u ->
                GlobalData.user = u
                true
            } ?: false
        }

        val refreshSession: suspend (String) -> Unit = { token ->
            val userSession = SupabaseManager.refreshSession(token)
            prefs.edit()
                .putString(accessTokenPrefKey, userSession?.accessToken)
                .putString(refreshTokenPrefKey, userSession?.refreshToken)
                .apply()
            userSession?.user?.let {
                fetchUser(it.id)
            }
        }

        if (accessToken.isNotBlank()) {
            val userInfo = SupabaseManager.retrieveUser(accessToken)
            userInfo?.let {
                fetchUser(it.id)
            } ?: if (refreshToken.isNotBlank()) {
                refreshSession(refreshToken)
            } else {
                viewModel.userIsConnected.value = false
                // TODO user is disconnected
            }
        } else if (refreshToken.isNotBlank()) {
            refreshSession(refreshToken)
        } else {
            viewModel.userIsConnected.value = false
            // TODO user is disconnected
        }
    }

}