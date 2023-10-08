package me.cniekirk.mastodroid.feature.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.activityDefaultExit
import me.cniekirk.mastodroid.core.designsystem.activityDefaultPopEnter
import me.cniekirk.mastodroid.core.designsystem.enterAnimation
import me.cniekirk.mastodroid.core.designsystem.exitAnimation
import me.cniekirk.mastodroid.core.designsystem.popEnterAnimation
import me.cniekirk.mastodroid.core.designsystem.popExitAnimation
import me.cniekirk.mastodroid.feature.feed.FeedRoute

const val FEED_NAVIGATION_ROUTE = "feed_route"
const val SETTINGS_ROUTE = "settings_route"

fun NavController.navigateToFeed() = this.navigate(FEED_NAVIGATION_ROUTE)

fun NavGraphBuilder.feedScreen(
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit,
    onSettingsPressed: () -> Unit
) {
    composable(
        route = FEED_NAVIGATION_ROUTE,
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() },
        exitTransition = {
            // If navigating to settings
            if (targetState.destination.route.equals(SETTINGS_ROUTE, true)) {
                activityDefaultExit()
            } else {
                exitAnimation()
            }
        },
        popEnterTransition = {
            // If coming back from settings
            if (initialState.destination.route.equals(SETTINGS_ROUTE, true)) {
                activityDefaultPopEnter()
            } else {
                popEnterAnimation()
            }
        }
    ) {
        FeedRoute(
            navigateToLogin = { navigateToLogin() },
            onSuccess = { onSuccess() },
            onSettingsPressed = { onSettingsPressed() }
        )
    }
}