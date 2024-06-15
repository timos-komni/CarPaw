package com.timkom.carpaw

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.ui.BottomNavGraph
import com.timkom.carpaw.ui.BottomNavigationItem
import com.timkom.carpaw.ui.components.BottomNavigationBar
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
                // TODO remove
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationBar()
                }*/
                var navigationSelectedItem by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val navController = rememberNavController()
                val screenTitle: String by mainViewModel.screenTitle
                val onBackButton: (() -> Unit)? by mainViewModel.onBackButton
                val actions: (@Composable () -> Unit)? by mainViewModel.actions
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = screenTitle,
                                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ),
                            navigationIcon = {
                                onBackButton?.let {
                                    IconButton(onClick = it) {
                                        Icon(
                                            Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = "Back",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            },
                            actions = { actions?.invoke() }
                        )
                    },
                    bottomBar = {
                        val navItems = listOf(
                            BottomNavigationItem.Home,
                            BottomNavigationItem.Search,
                            BottomNavigationItem.CreateRide,
                            BottomNavigationItem.MyRides,
                            BottomNavigationItem.Profile,
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
            }
        }
    }
}