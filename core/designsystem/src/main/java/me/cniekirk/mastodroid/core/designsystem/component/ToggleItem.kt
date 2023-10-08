package me.cniekirk.mastodroid.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.R

@Composable
fun ToggleItem(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    title: String,
    subText: String,
    onToggle: (Boolean) -> Unit
) {
    val stateEnabled = stringResource(R.string.enabled)
    val stateNotEnabled = stringResource(R.string.not_enabled)

    Row(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                stateDescription = if (enabled) stateEnabled else stateNotEnabled
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.widthIn()) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(0.75f),
                text = subText,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Switch(checked = enabled, onCheckedChange = { onToggle(it) })
    }
}

@Preview
@Composable
fun ToggleItemEnabledPreview() {
    MastodroidTheme {
        Surface {
            ToggleItem(enabled = true, title = "Notifications", subText = "Receive notifications about posts & comments", onToggle = {})
        }
    }
}

@Preview
@Composable
fun ToggleItemDisabledPreview() {
    MastodroidTheme {
        Surface {
            ToggleItem(enabled = false, title = "Notifications", subText = "Receive notifications about posts & comments", onToggle = {})
        }
    }
}