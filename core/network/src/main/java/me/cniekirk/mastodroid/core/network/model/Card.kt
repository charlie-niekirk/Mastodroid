package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(
    @Json(name = "author_name")
    val authorName: String?,
    @Json(name = "author_url")
    val authorUrl: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "embed_url")
    val embedUrl: String?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "html")
    val html: String?,
    @Json(name = "image")
    val image: Any?,
    @Json(name = "provider_name")
    val providerName: String?,
    @Json(name = "provider_url")
    val providerUrl: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "width")
    val width: Int?
)