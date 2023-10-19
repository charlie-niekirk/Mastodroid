package me.cniekirk.mastodroid.core.designsystem.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
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
    userFeedItem: UserFeedItem?,
    onItemClick: (postId: String) -> Unit
) {
    val item = if (userFeedItem?.rebloggedPost != null) {
        userFeedItem.rebloggedPost as UserFeedItem
    } else {
        userFeedItem
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(userFeedItem?.id.toString()) }
    ) {
        if (userFeedItem?.rebloggedPost != null) {
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
                    text = stringResource(id = R.string.reblogged, item?.userName ?: ""),
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
                    .data(item?.userProfilePictureUrl)
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
                    text = item?.userName ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "@${item?.userHandle}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = item?.timeSincePost ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = item?.content as AnnotatedString,
            style = MaterialTheme.typography.bodyLarge
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
fun MastodonStatusPreview(@PreviewParameter(LoremIpsum30Words::class) text: String) {
    val feedItem = UserFeedItem(
        1,
        "Example User",
        "example",
        "",
        "1hr",
        AnnotatedString("$text."),
        11,
        12,
        13,
        persistentListOf()
    )
    MastodroidTheme {
        Surface {
            MastodonStatus(userFeedItem = feedItem) {}
        }
    }
}