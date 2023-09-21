package me.cniekirk.mastodroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint
import me.cniekirk.mastodroid.core.AppRoot
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MastodroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val displayFeatures = calculateDisplayFeatures(this)

                    AppRoot(
                        windowSizeClass = windowSizeClass,
                        displayFeatures = displayFeatures
                    )
                }
            }
        }
    }
}