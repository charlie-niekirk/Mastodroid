package me.cniekirk.mastodroid.core.model

data class MastodonInstance(
    val name: String,
    val numUsers: Int,
    val description: String,
    val thumbnailUrl: String
)
