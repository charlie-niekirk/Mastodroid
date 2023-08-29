package me.cniekirk.mastodroid.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Instance(
    @Json(name = "active_users")
    val activeUsers: Int?,
    @Json(name = "added_at")
    val addedAt: String?,
    @Json(name = "admin")
    val admin: String?,
    @Json(name = "checked_at")
    val checkedAt: String?,
    @Json(name = "connections")
    val connections: String?,
    @Json(name = "dead")
    val dead: Boolean?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "https_rank")
    val httpsRank: String?,
    @Json(name = "https_score")
    val httpsScore: Int?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "info")
    val info: Info?,
    @Json(name = "ipv6")
    val ipv6: Boolean?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "obs_rank")
    val obsRank: String?,
    @Json(name = "obs_score")
    val obsScore: Int?,
    @Json(name = "open_registrations")
    val openRegistrations: Boolean?,
    @Json(name = "statuses")
    val statuses: String?,
    @Json(name = "thumbnail")
    val thumbnail: String?,
    @Json(name = "thumbnail_proxy")
    val thumbnailProxy: String?,
    @Json(name = "up")
    val up: Boolean?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "uptime")
    val uptime: Int?,
    @Json(name = "users")
    val users: String?,
    @Json(name = "version")
    val version: String?
)