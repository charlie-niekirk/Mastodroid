package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Application(
    @Json(name = "name")
    val name: String?,
    @Json(name = "website")
    val website: Any?
)