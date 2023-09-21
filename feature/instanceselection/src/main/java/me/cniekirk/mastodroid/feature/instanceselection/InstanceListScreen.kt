package me.cniekirk.mastodroid.feature.instanceselection

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.model.MastodonInstance
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun InstanceRoute(viewModel: InstanceListViewModel = hiltViewModel()) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { effect ->
        when (effect) {
            InstanceListEffect.InstanceSelected -> {

            }
            is InstanceListEffect.ShowError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    InstanceScreen(
        state = state,
        onBackPressed = { /*TODO*/ },
        onQueryChanged = viewModel::queryChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstanceScreen(
    state: InstanceListState,
    onBackPressed: () -> Unit,
    onQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.login_title))
            },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { onBackPressed() },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            value = state.query,
            onValueChange = { onQueryChanged(it) },
            label = { Text(text = stringResource(id = R.string.server_search_label)) }
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.servers) {
                ServerItem(server = it)
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun ServerItem(server: MastodonInstance) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                text = server.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 8.dp),
                text = stringResource(id = R.string.users_format, server.numUsers),
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun InstanceScreenPreview() {
    val servers = persistentListOf(
        MastodonInstance(name= "mastodon.social", description = "Great server", numUsers = 100, thumbnailUrl = ""),
        MastodonInstance(name= "uk.social", description = "Great server", numUsers = 37, thumbnailUrl = ""),
        MastodonInstance(name= "something.social", description = "Great server", numUsers = 42, thumbnailUrl = "")
    )
    MastodroidTheme {
        Surface {
            InstanceScreen(
                state = InstanceListState(query = "mastodon", servers = servers),
                onBackPressed = { /* no-op */ },
                onQueryChanged = { /* no-op */ }
            )
        }
    }
}