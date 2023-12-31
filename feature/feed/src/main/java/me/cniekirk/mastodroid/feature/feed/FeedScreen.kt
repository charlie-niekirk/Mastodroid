package me.cniekirk.mastodroid.feature.feed

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.component.MastodonStatus
import me.cniekirk.mastodroid.core.model.MediaType
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.feature.feed.ViewState.AUTH_ERROR
import me.cniekirk.mastodroid.feature.feed.ViewState.LOADING
import me.cniekirk.mastodroid.feature.feed.ViewState.SUCCESS
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun FeedRoute(
    viewModel: FeedViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit,
    onSettingsPressed: () -> Unit,
    onItemClicked: (postId: String) -> Unit
) {
    val context = LocalContext.current

    val state = viewModel.collectAsState().value
    viewModel.collectSideEffect(
        sideEffect = { effect ->
            handleSideEffect(
                effect,
                toast = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
            )
        }
    )

    when (state.viewState) {
        LOADING -> LoadingView()
        SUCCESS -> {
            LaunchedEffect(Unit) {
                onSuccess()
            }

            FeedScreen(
                state = state,
                onSettingsClicked = { onSettingsPressed() },
                onItemClicked = { onItemClicked(it) },
                onReplyClicked = {},
                onReblogClicked = {},
                onFavouriteClicked = {},
                onShareClicked = {}
            )
        }
        AUTH_ERROR -> {
            LaunchedEffect(state) {
                navigateToLogin()
            }
        }
    }
}

private fun handleSideEffect(
    feedEffect: FeedEffect,
    toast: (Int) -> Unit
) {
    when (feedEffect) {
        is FeedEffect.Error -> toast(feedEffect.message)
    }
}

@Composable
internal fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    state: FeedState,
    onSettingsClicked: () -> Unit,
    onItemClicked: (postId: String) -> Unit,
    onReplyClicked: (postId: String) -> Unit,
    onReblogClicked: (postId: String) -> Unit,
    onFavouriteClicked: (postId: String) -> Unit,
    onShareClicked: (postId: String) -> Unit
) {
    val items = state.feedItems.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.feed_title),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            actions = {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { onSettingsClicked() },
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        )

        when (items.loadState.refresh) {
            is LoadState.Error -> {
                // TODO
            }
            LoadState.Loading -> {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.weight(1f))
            }
            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items.itemCount,
                        key = items.itemKey { it.id }
                    ) { index ->
                        val feedItem = items[index]

                        if (feedItem != null) {
                            MastodonStatus(
                                userFeedItem = feedItem,
                                onItemClicked = { onItemClicked(it) },
                                onReplyClicked = { onReplyClicked(it) },
                                onReblogClicked = { onReblogClicked(it) },
                                onFavouriteClicked = { onFavouriteClicked(it) },
                                onShareClicked = { onShareClicked(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    val feedItem = UserFeedItem(
        1,
        "https://mastodon.social/@someone/112233",
        "Example User",
        "example",
        "",
        "1hr",
        "10 October 2023 10:34",
        "This is an example post and here's more text so that it spans more than one line.",
        11,
        12,
        13,
        "Mastodroid for Android",
        false,
        false,
        persistentListOf()
    )

    val state = FeedState(viewState = SUCCESS, feedItems = MutableStateFlow(PagingData.from(listOf(feedItem, feedItem.copy(id = 2), feedItem.copy(id = 3)))))
    MastodroidTheme {
        Surface {
            FeedScreen(state, {}, {}, {}, {}, {}, {})
        }
    }
}