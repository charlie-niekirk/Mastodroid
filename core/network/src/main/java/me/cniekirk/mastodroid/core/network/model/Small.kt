package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Small(
    @Json(name = "aspect")
    val aspect: Double?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "size")
    val size: String?,
    @Json(name = "width")
    val width: Int?
)