package me.cniekirk.mastodroid.features.core

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import me.cniekirk.mastodroid.ui.theme.ContentType
import me.cniekirk.mastodroid.ui.theme.NavigationType

@Composable
fun AppRoot(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {
    val navigationType: NavigationType
    val contentType: ContentType

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            // Tablet/foldable oriented UI
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.DUAL_PANE
        }
        else -> {
            // Normal phone oriented UI
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }

    val navController = rememberNavController()

    MastodroidNavHost(
        navController = navController,
        contentType = contentType,
        displayFeatures = displayFeatures,
        isExpandedWindowSize = navigationType == NavigationType.NAVIGATION_RAIL,
        navigationType = navigationType
    )
}