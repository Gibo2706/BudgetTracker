package pmf.it.app.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.systemGestureExclusion
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.ui.Screen
import pmf.it.app.budgettracker.ui.screen.HomeScreen
import pmf.it.app.budgettracker.ui.screen.LoginScreen
import pmf.it.app.budgettracker.ui.screen.PlanScreen
import pmf.it.app.budgettracker.ui.screen.ProfileScreen
import pmf.it.app.budgettracker.ui.screen.RegisterScreen
import pmf.it.app.budgettracker.ui.theme.BudgetTrackerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefManager = PreferencesManager(this)
        val token = prefManager.getData("token", "")
        setContent {
            BudgetTrackerTheme {
                val systemBarStyle by remember {
                    val defaultSystemBarColor = android.graphics.Color.TRANSPARENT
                    mutableStateOf(
                        SystemBarStyle.auto(
                            lightScrim = defaultSystemBarColor,
                            darkScrim = defaultSystemBarColor
                        )
                    )
                }
                LaunchedEffect(systemBarStyle) {
                    enableEdgeToEdge(
                        navigationBarStyle = systemBarStyle,
                    )
                }
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val items = listOf(
                    Screen.Home,
                    Screen.Plan,
                    Screen.Profile
                )
                Scaffold(
                    modifier = Modifier
                        .systemGestureExclusion()
                        .fillMaxSize(),
                    bottomBar = {
                        val bottomBarState = remember { mutableStateOf(true) }
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        when(navBackStackEntry?.destination?.route) {
                            Screen.Login.route -> {
                                bottomBarState.value = false
                            }
                            Screen.Register.route -> {
                                bottomBarState.value = false
                            }
                            else -> {
                                bottomBarState.value = true
                            }
                        }

                        if(bottomBarState.value) {
                            BottomNavigation(
                                modifier = Modifier.systemBarsPadding(),
                            ) {
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surface),
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon ?: Icons.Default.Favorite,
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(screen.resource) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                navController.graph.startDestinationRoute?.let { route ->
                                                    popUpTo(route) {
                                                        saveState = true
                                                    }
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                ) {innerPadding ->
                    NavHost(
                        navController,
                        startDestination = if (token.isNotEmpty()) Screen.Home.route else Screen.Login.route,
                        Modifier.padding(innerPadding))
                    {
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }
                        composable(Screen.Plan.route) {
                            PlanScreen()
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController = navController)
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(navController = navController)
                        }
                        composable(Screen.Register.route){
                            RegisterScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudgetTrackerTheme {
        Greeting("Android")
    }
}