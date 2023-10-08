package me.cniekirk.mastodroid.feature.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.component.ToggleItem
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state = viewModel.collectAsState().value

    SettingsScreen(
        disableAnimatedProfileImages = false,
        onDisableAnimatedProfileImagesClicked = { /*TODO*/ },
        disableAnimatedEmoji = false,
        onDisableAnimatedEmojiClicked = { /*TODO*/ }
    )
}

@Composable
fun SettingsScreen(
    disableAnimatedProfileImages: Boolean,
    onDisableAnimatedProfileImagesClicked: () -> Unit,
    disableAnimatedEmoji: Boolean,
    onDisableAnimatedEmojiClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(start = 32.dp, top = 32.dp),
            text = stringResource(id = R.string.settings_title),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier.padding(start = 32.dp, top = 32.dp),
            text = stringResource(id = R.string.look_and_feel_heading),
            style = MaterialTheme.typography.bodySmall
        )

        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            enabled = disableAnimatedProfileImages,
            title = stringResource(id = R.string.animated_profile_images_title),
            subText = stringResource(id = R.string.animated_profile_images_subtext),
            onToggle = { onDisableAnimatedProfileImagesClicked() }
        )
        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            enabled = disableAnimatedEmoji,
            title = stringResource(id = R.string.animated_emoji_title),
            subText = stringResource(id = R.string.animated_emoji_subtext),
            onToggle = { onDisableAnimatedEmojiClicked() }
        )

        Text(
            modifier = Modifier.padding(start = 32.dp, top = 32.dp),
            text = stringResource(id = R.string.notifications_heading),
            style = MaterialTheme.typography.bodySmall
        )

        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            enabled = disableAnimatedProfileImages,
            title = stringResource(id = R.string.follow_title),
            subText = stringResource(id = R.string.follow_subtext),
            onToggle = { onDisableAnimatedProfileImagesClicked() }
        )
        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            enabled = disableAnimatedEmoji,
            title = stringResource(id = R.string.reblog_title),
            subText = stringResource(id = R.string.reblog_subtext),
            onToggle = { onDisableAnimatedEmojiClicked() }
        )
        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            enabled = disableAnimatedProfileImages,
            title = stringResource(id = R.string.mention_title),
            subText = stringResource(id = R.string.metion_subtext),
            onToggle = { onDisableAnimatedProfileImagesClicked() }
        )
        ToggleItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            enabled = disableAnimatedEmoji,
            title = stringResource(id = R.string.favourite_title),
            subText = stringResource(id = R.string.favourite_subtext),
            onToggle = { onDisableAnimatedEmojiClicked() }
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    MastodroidTheme {
        Surface {
            SettingsScreen(false, {}, false, {})
        }
    }
}