package me.cniekirk.mastodroid.feature.feed

import android.text.Html
import android.view.ViewStructure.HtmlInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import de.charlex.compose.HtmlText
import de.charlex.compose.toAnnotatedString
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.feature.feed.ViewState.*
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

@Composable
internal fun FeedRoute(
    viewModel: FeedViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    val state = viewModel.collectAsState().value

    when (state.viewState) {
        LOADING -> LoadingView()
        SUCCESS -> {
            LaunchedEffect(Unit) {
                onSuccess()
            }
            FeedScreen(state)
        }
        AUTH_ERROR -> {
            LaunchedEffect(state) {
                navigateToLogin()
            }
        }
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
internal fun FeedScreen(state: FeedState) {
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
                    modifier = Modifier.padding(end = 16.dp),
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
            else -> {
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
                            MastodonStatus(userFeedItem = feedItem)
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun MastodonStatus(userFeedItem: UserFeedItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userFeedItem.userProfilePictureUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.profile_image_cd),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = userFeedItem.userName,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "@${userFeedItem.userHandle}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = Html.fromHtml(userFeedItem.content, HtmlCompat.FROM_HTML_MODE_COMPACT).toAnnotatedString(),
            style = MaterialTheme.typography.bodyLarge
        )

        if (userFeedItem.mediaInfo.isNotEmpty()) {
            // If there is media, show it
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(userFeedItem.mediaInfo.first().ratio),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userFeedItem.mediaInfo.first().url)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription =
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(start = 4.dp),
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(bottom = 3.dp, start = 8.dp),
                    text = userFeedItem.numComments.toString()
                )
            }
        }

        HorizontalDivider()
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    val state = FeedState()
    MastodroidTheme {
        Surface {
            FeedScreen(state)
        }
    }
}