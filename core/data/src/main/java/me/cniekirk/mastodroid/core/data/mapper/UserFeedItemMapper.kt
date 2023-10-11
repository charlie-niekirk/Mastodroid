package me.cniekirk.mastodroid.core.data.mapper

import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.style.ParagraphStyle
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.core.model.MediaInfo
import me.cniekirk.mastodroid.core.model.MediaType
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.network.model.NetworkStatus
import timber.log.Timber
import java.time.Instant
import kotlin.math.absoluteValue

fun NetworkStatus.toUserFeedItem(): UserFeedItem {

    val media = this.mediaAttachments?.map { media ->
        Timber.d("Aspect: ${media.meta?.original?.aspect}, Float: ${media.meta?.original?.aspect?.toFloat()}")
        MediaInfo(
            if (media.type?.equals("video", true) == true) MediaType.VIDEO else MediaType.IMAGE,
            media.meta?.original?.aspect?.toFloat() ?: 0f,
            media.url ?: ""
        )
    }

    val html = Html.fromHtml(this.content, HtmlCompat.FROM_HTML_MODE_LEGACY).removeTrailingWhitespace()


    return UserFeedItem(
        id = this.id?.toLong() ?: 0L,
        userName = this.account?.displayName ?: "",
        userHandle = this.account?.username ?: "",
        userProfilePictureUrl = this.account?.avatar ?: "",
        timeSincePost = Instant.parse(this.createdAt).toEpochMilli().getElapsedTime(),
        content = html,
        numComments = this.repliesCount ?: 0,
        numReblogs = this.reblogsCount ?: 0,
        numFavourites = this.favouritesCount ?: 0,
        mediaInfo = media?.toImmutableList() ?: persistentListOf(),
        rebloggedPost = if (this.reblog != null) this.reblog?.toUserFeedItem() else null
    )
}

private const val SECOND_MILLIS: Long = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
private const val MONTH_MILLIS = 30 * DAY_MILLIS
private const val YEAR_MILLIS = 12 * MONTH_MILLIS

private fun Long.getElapsedTime(): String {
    val now = System.currentTimeMillis()
    val diff = (now - this).absoluteValue
    return when {
        diff < MINUTE_MILLIS -> {
            "now"
        }
        diff < 2 * MINUTE_MILLIS -> {
            "1m"
        }
        diff < 50 * MINUTE_MILLIS -> {
            "${diff / MINUTE_MILLIS}m"
        }
        diff < 120 * MINUTE_MILLIS -> {
            "1h"
        }
        diff < 24 * HOUR_MILLIS -> {
            "${diff / HOUR_MILLIS}h"
        }
        diff < 48 * HOUR_MILLIS -> {
            "1d"
        }
        diff < MONTH_MILLIS -> {
            "${diff / DAY_MILLIS}d"
        }
        diff < 2 * MONTH_MILLIS -> {
            "1M"
        }
        diff < YEAR_MILLIS -> {
            "${diff / MONTH_MILLIS}M"
        }
        diff < 2 * YEAR_MILLIS -> {
            "1Y"
        }
        else -> {
            "${diff / YEAR_MILLIS}Y"
        }
    }
}

private fun Spanned.removeTrailingWhitespace(): Spanned {
    var i = this.length

    while (i-- > 0 && this[i].isWhitespace()) {
    }

    return this.subSequence(0, i + 1) as Spanned
}