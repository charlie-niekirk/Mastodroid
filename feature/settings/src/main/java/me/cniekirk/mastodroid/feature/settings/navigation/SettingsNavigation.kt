package me.cniekirk.mastodroid.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.activityDefaultEnter
import me.cniekirk.mastodroid.core.designsystem.activityDefaultPopExit
import me.cniekirk.mastodroid.feature.settings.SettingsRoute

const val SETTINGS_NAVIGATION_ROUTE = "settings_route"

fun NavController.navigateToSettings() = this.navigate(SETTINGS_NAVIGATION_ROUTE)

fun NavGraphBuilder.settingsScreen(goBack: () -> Unit) {
    composable(
        route = SETTINGS_NAVIGATION_ROUTE,
        enterTransition = { activityDefaultEnter() },
        popExitTransition = { activityDefaultPopExit() }
    ) {
        SettingsRoute()
    }
}