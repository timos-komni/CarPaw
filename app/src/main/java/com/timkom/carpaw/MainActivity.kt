package com.timkom.carpaw

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.ui.BottomNavGraph
import com.timkom.carpaw.ui.BottomNavigationItem
import com.timkom.carpaw.ui.components.BottomNavigationBar
import com.timkom.carpaw.ui.theme.CarPawTheme
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationBar()
                }
                var navigationSelectedItem by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("CarPaw") },
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
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