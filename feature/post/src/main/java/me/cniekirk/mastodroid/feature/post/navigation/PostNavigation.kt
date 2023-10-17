package me.cniekirk.mastodroid.feature.post.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.cniekirk.mastodroid.core.designsystem.enterAnimation
import me.cniekirk.mastodroid.core.designsystem.popExitAnimation
import me.cniekirk.mastodroid.feature.post.PostRoute

private const val POST_ID_ARG_ID = "postId"
private const val POST_NAVIGATION_ROUTE = "post"

fun NavController.navigateToPost(postId: String) = this.navigate("$POST_NAVIGATION_ROUTE/$postId")

internal class PostArgs(val postId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[POST_ID_ARG_ID]) as String)
}

fun NavGraphBuilder.postScreen(
    onBackPressed: () -> Unit
) {
    composable(
        route = "$POST_NAVIGATION_ROUTE/{$POST_ID_ARG_ID}",
        enterTransition = { enterAnimation() },
        popExitTransition = { popExitAnimation() }
    ) {
        PostRoute {
            onBackPressed()
        }
    }
}