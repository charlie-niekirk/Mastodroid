package me.cniekirk.mastodroid.feature.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.enterAnimation
import me.cniekirk.mastodroid.core.designsystem.popExitAnimation
import me.cniekirk.mastodroid.feature.feed.FeedRoute

const val FEED_NAVIGATION_ROUTE = "feed_route"

fun NavController.navigateToFeed() = this.navigate(FEED_NAVIGATION_ROUTE)

fun NavGraphBuilder.feedScreen(
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    composable(
        route = FEED_NAVIGATION_ROUTE,
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() }
    ) {
        FeedRoute {
            navigateToLogin()
        }
    }
}