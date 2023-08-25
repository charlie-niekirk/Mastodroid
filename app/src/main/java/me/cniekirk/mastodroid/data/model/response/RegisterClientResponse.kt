package me.cniekirk.mastodroid.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterClientResponse(
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "client_secret")
    val clientSecret: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "redirect_uri")
    val redirectUri: String,
    @Json(name = "vapid_key")
    val vapidKey: String,
    @Json(name = "website")
    val website: String
)