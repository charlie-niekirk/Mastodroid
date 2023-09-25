package me.cniekirk.mastodroid.feature.codereceiver

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CodeReceiverRoute(
    viewModel: CodeReceiverViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CodeReceiverEffect.Error -> {
                Toast.makeText(context, sideEffect.errorMessage, Toast.LENGTH_SHORT).show()
            }
            CodeReceiverEffect.Success -> { onSuccess() }
        }
    }

    CodeReceiverScreen(state = state)
}

@Composable
internal fun CodeReceiverScreen(state: CodeReceiverState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = state.loadingMessage)
        )
    }
}

@Preview
@Composable
internal fun CodeReceiverScreenPreview() {
    MastodroidTheme {
        Surface {
            CodeReceiverScreen(state = CodeReceiverState(loadingMessage = R.string.logging_you_in))
        }
    }
}