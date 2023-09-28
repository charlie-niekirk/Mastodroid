package me.cniekirk.mastodroid.feature.feed

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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.feature.feed.ViewState.AUTH_ERROR
import me.cniekirk.mastodroid.feature.feed.ViewState.LOADING
import me.cniekirk.mastodroid.feature.feed.ViewState.SUCCESS
import org.orbitmvi.orbit.compose.collectAsState

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
    val item = if (userFeedItem.rebloggedPost != null) {
        userFeedItem.rebloggedPost as UserFeedItem
    } else {
        userFeedItem
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (userFeedItem.rebloggedPost != null) {
            Row(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {  }
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Repeat,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                    text = stringResource(id = R.string.reblogged, item.userName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.userProfilePictureUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.profile_image_cd),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = item.userName,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "@${item.userHandle}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = item.timeSincePost,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = item.content as AnnotatedString,
            style = MaterialTheme.typography.bodyLarge
        )

        if (item.mediaInfo.isNotEmpty()) {
            // If there is media, show it
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(item.mediaInfo.first().ratio),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.mediaInfo.first().url)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .zIndex(1f)
                        .clickable { },
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = stringResource(id = R.string.replies_cd)
                )
                Text(
                    modifier = Modifier.padding(bottom = 3.dp, start = 8.dp),
                    text = item.numComments.toString()
                )
            }
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .zIndex(1f)
                        .clickable { },
                    imageVector = Icons.Default.Repeat,
                    contentDescription = stringResource(id = R.string.reblog_cd)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = item.numReblogs.toString()
                )
            }
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .zIndex(1f)
                        .clickable { },
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favourites_cd)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = item.numFavourites.toString()
                )
            }
            Icon(
                modifier = Modifier.clickable {  },
                imageVector = Icons.Default.Share,
                contentDescription = stringResource(id = R.string.share_cd)
            )
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