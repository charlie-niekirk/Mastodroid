package me.cniekirk.mastodroid.core.model

import kotlinx.collections.immutable.ImmutableList

data class UserStatusAndContext(
    val status: UserFeedItem,
    val context: ImmutableList<UserFeedItem>
)