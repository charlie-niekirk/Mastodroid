package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkStatus(
    @Json(name = "account")
    val account: Account?,
    @Json(name = "application")
    val application: Application?,
    @Json(name = "bookmarked")
    val bookmarked: Boolean?,
    @Json(name = "card")
    val card: Card?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "emojis")
    val emojis: List<Any?>?,
    @Json(name = "favourited")
    val favourited: Boolean?,
    @Json(name = "favourites_count")
    val favouritesCount: Int?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "in_reply_to_account_id")
    val inReplyToAccountId: Any?,
    @Json(name = "in_reply_to_id")
    val inReplyToId: Any?,
    @Json(name = "language")
    val language: String?,
    @Json(name = "media_attachments")
    val mediaAttachments: List<MediaAttachment>?,
    @Json(name = "mentions")
    val mentions: List<Any?>?,
    @Json(name = "muted")
    val muted: Boolean?,
    @Json(name = "poll")
    val poll: Any?,
    @Json(name = "reblog")
    val reblog: NetworkStatus?,
    @Json(name = "reblogged")
    val reblogged: Boolean?,
    @Json(name = "reblogs_count")
    val reblogsCount: Int?,
    @Json(name = "replies_count")
    val repliesCount: Int?,
    @Json(name = "sensitive")
    val sensitive: Boolean?,
    @Json(name = "spoiler_text")
    val spoilerText: String?,
    @Json(name = "tags")
    val tags: List<Any?>?,
    @Json(name = "uri")
    val uri: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "visibility")
    val visibility: String?
)