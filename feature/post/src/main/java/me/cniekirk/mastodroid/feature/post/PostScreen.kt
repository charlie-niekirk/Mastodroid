package me.cniekirk.mastodroid.feature.post

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.cniekirk.mastodroid.core.common.util.getUriFromBitmap
import me.cniekirk.mastodroid.core.common.util.shareMedia
import me.cniekirk.mastodroid.core.common.util.shareText
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.component.MastodonStatus
import me.cniekirk.mastodroid.core.designsystem.preview.LoremIpsum30Words
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.ui.loadImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostRoute(
    viewModel: PostViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    val sheetState = rememberModalBottomSheetState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PostEffect.Error -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            is PostEffect.ReplyToPost -> {

            }
            is PostEffect.ShareMedia -> {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        viewModel.onDismissShare()
                    }
                }
                scope.launch {
                    val uris = arrayListOf<Uri>()
                    sideEffect.mediaUrls.forEach {
                        context.loadImage(it) { bmp -> uris.add(context.getUriFromBitmap(bmp)) }
                    }
                    context.shareMedia(uris)
                }
            }
            is PostEffect.ShareLink -> {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        viewModel.onDismissShare()
                    }
                }
                context.shareText(sideEffect.link)
            }
        }
    }

    PostScreen(
        state = state,
        sheetState = sheetState,
        onBackPressed = { onBackPressed() },
        onReplyClicked = viewModel::replyPost,
        onReblogClicked = viewModel::reblogPost,
        onFavouriteClicked = viewModel::favouritePost,
        onShareClicked = viewModel::sharePost,
        onShareLinkClicked = viewModel::shareLink,
        onShareMediaClicked = viewModel::shareMedia,
        onDismissShare = viewModel::onDismissShare
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    state: PostState,
    sheetState: SheetState,
    onBackPressed: () -> Unit,
    onReplyClicked: (postId: String) -> Unit,
    onReblogClicked: (postId: String) -> Unit,
    onFavouriteClicked: (postId: String) -> Unit,
    onShareClicked: (postId: String) -> Unit,
    onShareLinkClicked: () -> Unit,
    onShareMediaClicked: () -> Unit,
    onDismissShare: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (state.bottomShareSheet.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { onDismissShare() },
            sheetState = sheetState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onShareLinkClicked() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(28.dp),
                        imageVector = Icons.Default.ContentCopy,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                        text = stringResource(id = R.string.share_link_text)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onShareMediaClicked() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(28.dp),
                        imageVector = Icons.Default.Image,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                        text = stringResource(id = R.string.share_media_text)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.post_title),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onBackPressed() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_cd)
                    )
                }
            }
        )

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(1f))
            CircularProgressIndicator(modifier = Modifier.testTag("post_loader"))
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.testTag("post_page_content"),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (state.post != null) {
                    item {
                        MastodonStatus(
                            userFeedItem = state.post,
                            onItemClicked = {},
                            onReplyClicked = {},
                            onReblogClicked = {},
                            onShareClicked = { onShareClicked(state.post.id.toString()) },
                            onFavouriteClicked = { onFavouriteClicked(state.post.id.toString()) },
                            isPost = true
                        )
                    }
                }

                item {
                    PostAnalytics(
                        numReposts = state.post?.numReblogs ?: 0,
                        numFavourites = state.post?.numReblogs ?: 0,
                        postTime = state.post?.timeString ?: "",
                        postClient = state.post?.client ?: ""
                    )

                    HorizontalDivider()
                }

                if (state.areCommentsLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    items(state.comments) { item ->
                        MastodonStatus(
                            userFeedItem = item,
                            onItemClicked = {},
                            onReplyClicked = {},
                            onReblogClicked = {},
                            onShareClicked = {},
                            onFavouriteClicked = {}
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostAnalytics(
    numReposts: Int,
    numFavourites: Int,
    postTime: String,
    postClient: String
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PostAnalyticItem(
            icon = Icons.Default.Repeat,
            text = stringResource(id = R.string.reposts_format, numReposts)
        )
        PostAnalyticItem(
            icon = Icons.Default.Favorite,
            text = stringResource(id = R.string.favourites_format, numFavourites)
        )
        PostAnalyticItem(
            icon = Icons.Default.Analytics,
            text = stringResource(id = R.string.client_time_format, postTime, postClient)
        )
    }
}

@Composable
fun PostAnalyticItem(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun PostAnalyticsPreview() {
    MastodroidTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                PostAnalytics(
                    numReposts = 10,
                    numFavourites = 18,
                    postTime = "20 October 2023 10:34",
                    postClient = "Mastodroid for Android"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PostScreenPreview(@PreviewParameter(LoremIpsum30Words::class) text: String) {
    val state = PostState(
        isLoading = false,
        post = UserFeedItem(
            1,
            "https://mastodon.social/@someone/112233",
            "Example User",
            "exampleuser",
            "",
            "1hr",
            "10 October 2023 10:34",
            text,
            11,
            12,
            13,
            "Mastodroid for Android",
            false,
            false,
            persistentListOf()
        ),
        areCommentsLoading = false
    )
    MastodroidTheme {
        Surface {
            PostScreen(state = state, sheetState = rememberModalBottomSheetState(), onBackPressed = {}, {}, {}, {}, {}, {}, {}, {})
        }
    }
}