package me.cniekirk.mastodroid.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Field(
    @Json(name = "name")
    val name: String?,
    @Json(name = "value")
    val value: String?,
    @Json(name = "verified_at")
    val verifiedAt: String?
)