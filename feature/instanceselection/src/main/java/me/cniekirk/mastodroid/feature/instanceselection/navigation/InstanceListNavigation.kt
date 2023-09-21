package me.cniekirk.mastodroid.feature.instanceselection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.enterAnimation
import me.cniekirk.mastodroid.core.designsystem.popExitAnimation
import me.cniekirk.mastodroid.feature.instanceselection.InstanceRoute

const val INSTANCE_LIST_NAVIGATION_ROUTE = "instance_list_route"

fun NavController.navigateToInstanceList() = this.navigate(INSTANCE_LIST_NAVIGATION_ROUTE)

fun NavGraphBuilder.instanceListScreen(onBackPressed: () -> Unit) {
    composable(
        route = INSTANCE_LIST_NAVIGATION_ROUTE,
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() }
    ) {
        InstanceRoute(
            onBackPressed = { onBackPressed() }
        )
    }
}