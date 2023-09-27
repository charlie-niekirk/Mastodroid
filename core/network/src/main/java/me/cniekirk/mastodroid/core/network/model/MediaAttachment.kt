package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaAttachment(
    @Json(name = "blurhash")
    val blurhash: String?,
    @Json(name = "description")
    val description: Any?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "meta")
    val meta: Meta?,
    @Json(name = "preview_remote_url")
    val previewRemoteUrl: Any?,
    @Json(name = "preview_url")
    val previewUrl: String?,
    @Json(name = "remote_url")
    val remoteUrl: Any?,
    @Json(name = "text_url")
    val textUrl: Any?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?
)