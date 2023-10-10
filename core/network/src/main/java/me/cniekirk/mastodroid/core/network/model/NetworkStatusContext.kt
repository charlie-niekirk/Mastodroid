package me.cniekirk.mastodroid.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkStatusContext(
    @Json(name = "ancestors")
    val ancestors: List<NetworkStatus>,
    @Json(name = "descendants")
    val descendants: List<NetworkStatus>
)
