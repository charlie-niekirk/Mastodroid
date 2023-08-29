package me.cniekirk.mastodroid.features.home.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import me.cniekirk.mastodroid.R
import me.cniekirk.mastodroid.ui.theme.MastodroidTheme

@Composable
fun LandingScreen(
    onJoinDefaultClick: () -> Unit,
    onSelectServerClick: () -> Unit,
    onCancelClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 64.dp),
            text = stringResource(id = R.string.join_server_title),
            style = MaterialTheme.typography.titleLarge
        )
        Image(
            modifier = Modifier
                .size(320.dp)
                .padding(top = 64.dp),
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
            onClick = {}
        ) {
            Text(
                text = stringResource(id = R.string.join_default_server),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            onClick = {}
        ) {
            Text(
                text = stringResource(id = R.string.join_another_server),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp))
        TextButton(
            onClick = { onLoginClick() }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@PreviewDynamicColors
@PreviewFontScale
@PreviewLightDark
@Composable
fun OnboardingScreenContentPreview() {
    MastodroidTheme {
        Surface {
            LandingScreen(
                onJoinDefaultClick = { /* no-op */ },
                onSelectServerClick = { /* no-op */ },
                onCancelClick = { /* no-op */ },
                onLoginClick = { /* no-op */ }
            )
        }
    }
}