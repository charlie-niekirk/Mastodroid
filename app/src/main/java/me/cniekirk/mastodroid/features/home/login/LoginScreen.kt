package me.cniekirk.mastodroid.features.home.login

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.R
import me.cniekirk.mastodroid.domain.model.UiInstance
import me.cniekirk.mastodroid.ui.theme.MastodroidTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val state = viewModel.collectAsState().value
    
    viewModel.collectSideEffect { effect ->
        when (effect) {
            LoginEffect.LoginSuccess -> {
                // Handle login success
            }
        }
    }
    
    LoginScreenContent(
        state = state,
        onBackPressed = { /*TODO*/ },
        onQueryChanged = viewModel::queryChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    state: LoginState,
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
            items(
                items = state.servers,
                key = { server -> server.id }
            ) { server ->
                ServerItem(server = server)
            }
        }
    }
}

@Composable
fun ServerItem(server: UiInstance) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                text = server.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 8.dp),
                text = stringResource(id = R.string.users_format, server.users),
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun LoginScreenContentPreview() {
    val servers = persistentListOf(
        UiInstance(name= "mastodon.social", id = "1", users = 100),
        UiInstance(name= "uk.social", id = "2", users = 37),
        UiInstance(name= "something.social", id = "3", users = 42)
    )
    MastodroidTheme {
        Surface {
            LoginScreenContent(
                state = LoginState(query = "mastodon", servers = servers),
                onBackPressed = { /* no-op */ },
                onQueryChanged = { /* no-op */ }
            )
        }
    }
}