package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Role(
    @Json(name = "color")
    val color: String?,
    @Json(name = "highlighted")
    val highlighted: Boolean?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "permissions")
    val permissions: String?
)