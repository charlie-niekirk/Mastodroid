package me.cniekirk.mastodroid.features.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import androidx.window.layout.DisplayFeature
import me.cniekirk.mastodroid.features.home.login.LandingScreen
import me.cniekirk.mastodroid.features.home.login.LoginScreen
import me.cniekirk.mastodroid.features.home.login.LoginViewModel
import me.cniekirk.mastodroid.ui.theme.ContentType
import me.cniekirk.mastodroid.ui.theme.NavigationType

val tabs = listOf(TabDestination.Home, TabDestination.Search, TabDestination.Alerts, TabDestination.Profile)

@Composable
fun MastodroidNavHost(
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    isExpandedWindowSize: Boolean,
    navigationType: NavigationType,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    when (navigationType) {
        NavigationType.BOTTOM_NAVIGATION -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        tabs.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(item.icon, contentDescription = item.icon.name) },
                                label = { Text(item.label) },
                                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                onClick = {
                                    navController.navigate(item.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) {
                NavHost(
                    modifier = modifier.padding(it),
                    navController = navController,
                    startDestination = TabDestination.Home.route
                ) {
                    // TrackBuddy search screen
                    homeGraph(navController, isExpandedWindowSize, displayFeatures)
                }
            }
        }
        NavigationType.NAVIGATION_RAIL -> {

        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    isExpandedWindowSize: Boolean,
    features: List<DisplayFeature>
) {
    navigation(
        startDestination = HomeDestination.Landing.route,
        route = TabDestination.Home.route
    ) {
        composable(route = HomeDestination.FeedListDetail.route) {

        }
        composable(route = HomeDestination.Landing.route) {
            LandingScreen(
                onJoinDefaultClick = {},
                onSelectServerClick = {},
                onCancelClick = {},
                onLoginClick = {
                    navController.navigate(HomeDestination.Login.route)
                }
            )
        }
        composable(route = HomeDestination.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(viewModel = viewModel)
        }
    }
}

sealed class TabDestination(val label: String, val route: String, val icon: ImageVector) {

    data object Home : TabDestination(
        label = "Home",
        route = "home",
        icon = Icons.Default.Home
    )

    data object Search : TabDestination(
        label = "Search",
        route = "search",
        icon = Icons.Default.Search
    )

    data object Alerts : TabDestination(
        label = "Alerts",
        route = "alerts",
        icon = Icons.Default.Notifications
    )

    data object Profile : TabDestination(
        label = "Profile",
        route = "profile",
        icon = Icons.Default.Person
    )
}

sealed class HomeDestination(val route: String) {

    data object FeedListDetail : HomeDestination(route = "feedListDetail")

    data object Landing : HomeDestination(route = "landing")

    data object Login : HomeDestination(route = "login")
}