package me.cniekirk.mastodroid.core

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
import me.cniekirk.mastodroid.core.designsystem.ContentType
import me.cniekirk.mastodroid.core.designsystem.NavigationType
import me.cniekirk.mastodroid.feature.instanceselection.navigation.instanceListScreen
import me.cniekirk.mastodroid.feature.instanceselection.navigation.navigateToInstanceList
import me.cniekirk.mastodroid.feature.onboarding.navigation.ONBOARDING_NAVIGATION_ROUTE
import me.cniekirk.mastodroid.feature.onboarding.navigation.onboardingScreen

val tabs = listOf(
    TabDestination.Home,
    TabDestination.Search,
    TabDestination.Alerts,
    TabDestination.Profile
)

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
        startDestination = ONBOARDING_NAVIGATION_ROUTE,
        route = TabDestination.Home.route
    ) {
        composable(route = HomeDestination.FeedListDetail.route) {

        }
        onboardingScreen(
            onJoinDefaultClicked = {},
            onSearchForServerClicked = {},
            onLoginClicked = { navController.navigateToInstanceList() }
        )
        instanceListScreen(
            onBackPressed = { navController.popBackStack() }
        )
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