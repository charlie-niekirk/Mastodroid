package me.cniekirk.mastodroid.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.exitAnimation
import me.cniekirk.mastodroid.core.designsystem.popEnterAnimation
import me.cniekirk.mastodroid.feature.onboarding.OnboardingScreen

const val ONBOARDING_NAVIGATION_ROUTE = "onboarding_route"

fun NavGraphBuilder.onboardingScreen(
    onJoinDefaultClicked: () -> Unit,
    onSearchForServerClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    composable(
        route = ONBOARDING_NAVIGATION_ROUTE,
        exitTransition = { exitAnimation() },
        popEnterTransition = { popEnterAnimation() }
    ) {
        OnboardingScreen(
            onJoinDefaultClicked = { onJoinDefaultClicked() },
            onSearchForServerClicked = { onSearchForServerClicked() },
            onLoginClicked = { onLoginClicked() }
        )
    }
}