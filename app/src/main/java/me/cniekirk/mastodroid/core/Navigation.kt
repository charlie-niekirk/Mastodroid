package me.cniekirk.mastodroid.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.window.layout.DisplayFeature
import me.cniekirk.mastodroid.core.designsystem.activityDefaultExit
import me.cniekirk.mastodroid.core.designsystem.activityDefaultPopEnter
import me.cniekirk.mastodroid.feature.codereceiver.navigation.codeReceiverScreen
import me.cniekirk.mastodroid.feature.feed.navigation.FEED_NAVIGATION_ROUTE
import me.cniekirk.mastodroid.feature.feed.navigation.feedScreen
import me.cniekirk.mastodroid.feature.feed.navigation.navigateToFeed
import me.cniekirk.mastodroid.feature.instanceselection.navigation.instanceListScreen
import me.cniekirk.mastodroid.feature.instanceselection.navigation.navigateToInstanceList
import me.cniekirk.mastodroid.feature.onboarding.navigation.navigateToOnboarding
import me.cniekirk.mastodroid.feature.onboarding.navigation.onboardingScreen
import me.cniekirk.mastodroid.feature.settings.navigation.navigateToSettings
import me.cniekirk.mastodroid.feature.settings.navigation.settingsScreen

val tabs = listOf(
    TabDestination.Home,
    TabDestination.Search,
    TabDestination.Alerts,
    TabDestination.Profile
)

@Composable
fun RootNavHost(
    navController: NavHostController,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RootDestinations.Tabs.route
    ) {
        composable(
            RootDestinations.Tabs.route,
            exitTransition = { activityDefaultExit() },
            popEnterTransition = { activityDefaultPopEnter() }
        ) {
            val innerController = rememberNavController()
            MastodroidTabContainer(
                navController = innerController,
                displayFeatures = displayFeatures,
                onSettingsPressed = { navController.navigateToSettings() }
            )
        }

        settingsScreen { navController.popBackStack() }
    }
}

@OptIn(
    ExperimentalMaterial3AdaptiveNavigationSuiteApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)
@Composable
fun MastodroidTabContainer(
    navController: NavHostController,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier,
    onSettingsPressed: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isBottomBarVisible = rememberSaveable { mutableStateOf(false) }
    val currentDestination = navBackStackEntry?.destination
    val navSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())

//    Scaffold(
//        bottomBar = {
//            AnimatedVisibility(
//                visible = isBottomBarVisible.value,
//                enter = slideInVertically(initialOffsetY = { it }),
//                exit = slideOutVertically(targetOffsetY = { -it })
//            ) {
//                NavigationBar {
//                    tabs.forEachIndexed { index, item ->
//                        NavigationBarItem(
//                            icon = { Icon(item.icon, contentDescription = item.icon.name) },
//                            label = { Text(item.label) },
//                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
//                            onClick = {
//                                navController.navigate(item.route) {
//                                    // Pop up to the start destination of the graph to
//                                    // avoid building up a large stack of destinations
//                                    // on the back stack as users select items
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    // Restore state when reselecting a previously selected item
//                                    restoreState = true
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    ) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            tabs.forEachIndexed { index, item ->
                item(
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
    ) {
        NavHost(
            navController = navController,
            startDestination = TabDestination.Home.route
        ) {
            // TrackBuddy search screen
            homeGraph(
                navController,
                displayFeatures,
                onSettingsPressed = { onSettingsPressed() },
                onChangeNavigationBarVisibility = { isVisible ->
                    isBottomBarVisible.value = isVisible
                }
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    features: List<DisplayFeature>,
    onSettingsPressed: () -> Unit,
    onChangeNavigationBarVisibility: (Boolean) -> Unit
) {
    navigation(
        startDestination = FEED_NAVIGATION_ROUTE,
        route = TabDestination.Home.route
    ) {
        feedScreen(
            navigateToLogin = { navController.navigateToOnboarding() },
            onSuccess = { onChangeNavigationBarVisibility(true) },
            onSettingsPressed = { onSettingsPressed() },
        )
        onboardingScreen(
            onJoinDefaultClicked = {},
            onSearchForServerClicked = {},
            onLoginClicked = { navController.navigateToInstanceList(true) }
        )
        instanceListScreen(
            onBackPressed = navController::popBackStack
        )
        codeReceiverScreen(
            tokenSaved = { navController.navigateToFeed() }
        )
    }
}

sealed class RootDestinations(val route: String) {

    data object Tabs : RootDestinations(
        route = "tabs"
    )

    data object Settings : RootDestinations(
        route = "settings"
    )
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