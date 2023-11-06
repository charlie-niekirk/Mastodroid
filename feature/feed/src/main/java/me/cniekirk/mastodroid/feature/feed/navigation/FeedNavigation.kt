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

fun NavController.navigateToFeed() = this.navigate(FEED_NAVIGATION_ROUTE)

fun NavGraphBuilder.feedScreen(
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit,
    onSettingsPressed: () -> Unit,
    onItemClicked: (postId: String) -> Unit
) {
    composable(
        route = FEED_NAVIGATION_ROUTE,
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() },
        exitTransition = { exitAnimation() },
        popEnterTransition = { popEnterAnimation() }
    ) {
        FeedRoute(
            navigateToLogin = { navigateToLogin() },
            onSuccess = { onSuccess() },
            onSettingsPressed = { onSettingsPressed() },
            onItemClicked = { onItemClicked(it) }
        )
    }
}