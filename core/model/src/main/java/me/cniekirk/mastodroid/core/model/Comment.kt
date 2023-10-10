package me.cniekirk.mastodroid.core.model

import kotlinx.collections.immutable.ImmutableList

data class Comment(
    val title: String,
    val content: String,
    val username: String,
    val displayName: String,
    val children: ImmutableList<Comment>,
)
