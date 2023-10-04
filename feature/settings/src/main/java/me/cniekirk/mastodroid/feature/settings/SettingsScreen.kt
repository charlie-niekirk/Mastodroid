package me.cniekirk.mastodroid.feature.settings

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

@Composable
fun SettingsRoute(viewModel: SettingsViewModel = hiltViewModel()) {

}

@Composable
fun SettingsScreen() {
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


    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    MastodroidTheme {
        Surface {
            SettingsScreen()
        }
    }
}