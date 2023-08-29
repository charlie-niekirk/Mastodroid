package me.cniekirk.mastodroid.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerList(
    @Json(name = "instances")
    val instances: List<Instance?>?,
    @Json(name = "pagination")
    val pagination: Pagination?
)