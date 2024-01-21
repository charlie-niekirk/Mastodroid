package me.cniekirk.mastodroid.core.designsystem.component

import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.R
import me.cniekirk.mastodroid.core.designsystem.preview.LoremIpsum30Words
import me.cniekirk.mastodroid.core.model.MediaType
import me.cniekirk.mastodroid.core.model.UserFeedItem

@Composable
fun MastodonStatus(
    userFeedItem: UserFeedItem,
    isPost: Boolean = false,
    onItemClicked: (post: UserFeedItem) -> Unit,
    onReplyClicked: (postId: String) -> Unit,
    onReblogClicked: (postId: String) -> Unit,
    onFavouriteClicked: (postId: String) -> Unit,
    onShareClicked: (postId: String) -> Unit
) {
    val context = LocalContext.current

    val item = if (userFeedItem.rebloggedPost != null) {
        userFeedItem.rebloggedPost as UserFeedItem
    } else {
        userFeedItem
    }

    val postId = userFeedItem.id.toString()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(item) }
    ) {
        if (userFeedItem.replyToUser.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .semantics(mergeDescendants = true) { }
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Reply,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.reply_to, item.replyToUser),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (userFeedItem.rebloggedPost != null) {
            Row(
                modifier = Modifier
                    .semantics(mergeDescendants = true) { }
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

        val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

        AndroidView(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            factory = { context -> TextView(context) },
            update = {
                it.text = Html.fromHtml(item.content, Html.FROM_HTML_MODE_COMPACT).removeTrailingWhitespace()
                it.typeface = context.resources.getFont(R.font.lexenddeca_regular)
                it.setTextColor(textColor)
            }
        )

        if (item.mediaInfo.isNotEmpty()) {
            // If there is media, show it
            when (item.mediaInfo.first().mediaType) {
                MediaType.IMAGE -> {
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
                MediaType.VIDEO -> {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.mediaInfo.first().url)
                            .crossfade(true)
                            .build(),
                        contentDescription = null
                    )
                }
            }
        }

        if (isPost) {
            PostFooter(
                onReplyClicked = { onReplyClicked(postId) },
                onReblogClicked = { onReblogClicked(postId) },
                onFavouriteClicked = { onFavouriteClicked(postId) },
                onShareClicked = { onShareClicked(postId) }
            )
        } else {
            ListPostFooter(
                item = item,
                onReplyClicked = { onReplyClicked(postId) },
                onReblogClicked = { onReblogClicked(postId) },
                onFavouriteClicked = { onFavouriteClicked(postId) },
                onShareClicked = { onShareClicked(postId) }
            )
        }

        HorizontalDivider()
    }
}

@Composable
internal fun ListPostFooter(
    item: UserFeedItem,
    onReplyClicked: () -> Unit,
    onReblogClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    val iconSize = 20.dp

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
                    .size(iconSize)
                    .padding(start = 4.dp)
                    .zIndex(1f)
                    .clickable { onReplyClicked() },
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
                    .size(iconSize)
                    .padding(start = 4.dp)
                    .zIndex(1f)
                    .clickable { onReblogClicked() },
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
                    .size(iconSize)
                    .padding(start = 4.dp)
                    .zIndex(1f)
                    .clickable { onFavouriteClicked() },
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.favourites_cd)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = item.numFavourites.toString()
            )
        }
        Icon(
            modifier = Modifier
                .size(iconSize)
                .clickable { onShareClicked() },
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(id = R.string.share_cd)
        )
    }
}

@Composable
internal fun PostFooter(
    onReplyClicked: () -> Unit,
    onReblogClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.clickable { onReplyClicked() },
            imageVector = Icons.Default.ChatBubble,
            contentDescription = stringResource(id = R.string.replies_cd)
        )
        Icon(
            modifier = Modifier.clickable { onReblogClicked() },
            imageVector = Icons.Default.Repeat,
            contentDescription = stringResource(id = R.string.reblog_cd)
        )
        Icon(
            modifier = Modifier.clickable { onFavouriteClicked() },
            imageVector = Icons.Default.Favorite,
            contentDescription = stringResource(id = R.string.favourites_cd)
        )
        Icon(
            modifier = Modifier.clickable { onShareClicked() },
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(id = R.string.share_cd)
        )
    }
}

private fun Spanned.removeTrailingWhitespace(): Spanned {
    var i = this.length

    while (i-- > 0 && this[i].isWhitespace()) {
    }

    return this.subSequence(0, i + 1) as Spanned
}

@Preview
@Composable
fun MastodonStatusPreview(@PreviewParameter(LoremIpsum30Words::class) text: String) {
    val feedItem = UserFeedItem(
        1,
        "https://mastodon.social/@someone/112233",
        "Example User",
        "example",
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
        persistentListOf(),
        "someuser"
    )
    MastodroidTheme {
        Surface {
            MastodonStatus(
                userFeedItem = feedItem,
                onItemClicked = {},
                onReplyClicked = {},
                onReblogClicked = {},
                onFavouriteClicked = {},
                onShareClicked = {}
            )
        }
    }
}