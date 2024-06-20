package com.timkom.carpaw

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.ui.BottomNavGraph
import com.timkom.carpaw.ui.FullScreenDialog
import com.timkom.carpaw.ui.LoginNavGraph
import com.timkom.carpaw.ui.SimpleFullScreenDialog
import com.timkom.carpaw.ui.components.buttons.ArrowBackButton
import com.timkom.carpaw.ui.data.BottomNavigationItem
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.MainViewModel
import com.timkom.carpaw.util.Either

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CarPawTheme(dynamicColor = false) {
                val mainViewModel: MainViewModel = viewModel()
                var navigationSelectedItem by mainViewModel.navigationSelectedItem
                val navController = rememberNavController()
                val screenTitle: String by mainViewModel.screenTitle
                val onBackButton: (() -> Unit)? by mainViewModel.onBackButton
                val actions: (@Composable () -> Unit)? by mainViewModel.actions
                val shouldHaveProfileAction: Boolean by mainViewModel.shouldHaveProfileAction
                var showLoginDialog : Boolean by mainViewModel.showLoginDialog
                var showProfileDialog by rememberSaveable {
                    mutableStateOf(false)
                }

                //detect the current orientation
                val configuration = LocalConfiguration.current
                val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

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
                                        if (!mainViewModel.userIsConnected.value) {
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
                            //BottomNavigationItem.Profile,
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
                    FullScreenDialog(onDismissRequest = { showLoginDialog = false }) {
                        val loginNavController = rememberNavController()
                        LoginNavGraph(navController = loginNavController)
                    }
                }
                
                if (showProfileDialog) {
                    SimpleFullScreenDialog(onDismissRequest = { showProfileDialog = false }) {
                        
                    }
                }
            }
        }
    }
}