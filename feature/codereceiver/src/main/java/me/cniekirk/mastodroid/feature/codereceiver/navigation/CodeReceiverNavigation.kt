package me.cniekirk.mastodroid.feature.codereceiver.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import me.cniekirk.mastodroid.feature.codereceiver.CodeReceiverRoute

private const val CODE_ARG_ID = "code"
const val URI = "https://verifymastodroidcode.com"
private const val CODE_RECEIVER_NAVIGATION_PREFIX = "code_receiver?$CODE_ARG_ID="
const val CODE_RECEIVER_NAVIGATION_ROUTE = "$CODE_RECEIVER_NAVIGATION_PREFIX{$CODE_ARG_ID}"

fun NavController.navigateToCodeReceiver(code: String) {
    this.navigate("$CODE_RECEIVER_NAVIGATION_PREFIX/$code")
}

internal class CodeReceiverArgs(val code: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[CODE_ARG_ID]) as String)
}

fun NavGraphBuilder.codeReceiverScreen(
    tokenSaved: () -> Unit
) {
    composable(
        route = CODE_RECEIVER_NAVIGATION_ROUTE,
        deepLinks = listOf(navDeepLink { uriPattern = "$URI?$CODE_ARG_ID={$CODE_ARG_ID}" })
    ) {
        CodeReceiverRoute {
            tokenSaved()
        }
    }
}