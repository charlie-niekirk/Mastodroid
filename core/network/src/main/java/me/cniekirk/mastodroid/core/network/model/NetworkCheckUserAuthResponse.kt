package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCheckUserAuthResponse(
    @Json(name = "acct")
    val acct: String?,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "avatar_static")
    val avatarStatic: String?,
    @Json(name = "bot")
    val bot: Boolean?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "discoverable")
    val discoverable: Any?,
    @Json(name = "display_name")
    val displayName: String?,
    @Json(name = "emojis")
    val emojis: List<Any?>?,
    @Json(name = "fields")
    val fields: List<Any?>?,
    @Json(name = "followers_count")
    val followersCount: Int?,
    @Json(name = "following_count")
    val followingCount: Int?,
    @Json(name = "group")
    val group: Boolean?,
    @Json(name = "header")
    val header: String?,
    @Json(name = "header_static")
    val headerStatic: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "last_status_at")
    val lastStatusAt: Any?,
    @Json(name = "locked")
    val locked: Boolean?,
    @Json(name = "noindex")
    val noindex: Boolean?,
    @Json(name = "note")
    val note: String?,
    @Json(name = "role")
    val role: Role?,
    @Json(name = "roles")
    val roles: List<Any?>?,
    @Json(name = "source")
    val source: Source?,
    @Json(name = "statuses_count")
    val statusesCount: Int?,
    @Json(name = "uri")
    val uri: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "username")
    val username: String?
)