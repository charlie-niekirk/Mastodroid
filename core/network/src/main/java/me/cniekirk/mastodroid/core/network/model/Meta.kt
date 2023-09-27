package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "original")
    val original: Original?,
    @Json(name = "small")
    val small: Small?
)