package me.cniekirk.mastodroid.core

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import me.cniekirk.mastodroid.core.designsystem.ContentType
import me.cniekirk.mastodroid.core.designsystem.NavigationType

@Composable
fun AppRoot(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {
//    val navigationType: NavigationType
//    val contentType: ContentType
//
//    when (windowSizeClass.widthSizeClass) {
//        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
//            // Tablet/foldable oriented UI
//            navigationType = NavigationType.NAVIGATION_RAIL
//            contentType = ContentType.DUAL_PANE
//        }
//        else -> {
//            // Normal phone oriented UI
//            navigationType = NavigationType.BOTTOM_NAVIGATION
//            contentType = ContentType.SINGLE_PANE
//        }
//    }

    val navController = rememberNavController()

    RootNavHost(
        navController = navController,
        displayFeatures = displayFeatures,
    )
}