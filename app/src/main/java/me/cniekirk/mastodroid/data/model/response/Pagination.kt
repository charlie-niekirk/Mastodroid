package me.cniekirk.mastodroid.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "next_id")
    val nextId: String?,
    @Json(name = "total")
    val total: Int?
)