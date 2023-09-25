package me.cniekirk.mastodroid.feature.instanceselection.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.cniekirk.mastodroid.core.designsystem.enterAnimation
import me.cniekirk.mastodroid.core.designsystem.popExitAnimation
import me.cniekirk.mastodroid.feature.instanceselection.InstanceRoute

const val IS_LOGIN_ARG_ID = "isLogin"
const val INSTANCE_LIST_NAVIGATION_ROUTE_PREFIX = "instance_list_route"
const val INSTANCE_LIST_NAVIGATION_ROUTE = "$INSTANCE_LIST_NAVIGATION_ROUTE_PREFIX/{$IS_LOGIN_ARG_ID}"

internal class InstanceListArgs(val isLogin: Boolean) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[IS_LOGIN_ARG_ID]) as Boolean)
}

fun NavController.navigateToInstanceList(isLogin: Boolean) =
    this.navigate("$INSTANCE_LIST_NAVIGATION_ROUTE_PREFIX/$isLogin")

fun NavGraphBuilder.instanceListScreen(onBackPressed: () -> Unit) {
    composable(
        route = INSTANCE_LIST_NAVIGATION_ROUTE,
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() },
        arguments = listOf(navArgument(IS_LOGIN_ARG_ID) { type = NavType.BoolType })
    ) {
        InstanceRoute(
            onBackPressed = { onBackPressed() }
        )
    }
}