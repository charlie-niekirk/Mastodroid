package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "discoverable")
    val discoverable: Any?,
    @Json(name = "fields")
    val fields: List<Any?>?,
    @Json(name = "follow_requests_count")
    val followRequestsCount: Int?,
    @Json(name = "hide_collections")
    val hideCollections: Any?,
    @Json(name = "indexable")
    val indexable: Boolean?,
    @Json(name = "language")
    val language: Any?,
    @Json(name = "note")
    val note: String?,
    @Json(name = "privacy")
    val privacy: String?,
    @Json(name = "sensitive")
    val sensitive: Boolean?
)