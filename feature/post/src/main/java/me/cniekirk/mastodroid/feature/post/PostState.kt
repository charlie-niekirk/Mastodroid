package me.cniekirk.mastodroid.feature.post

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.model.UserFeedItem

data class PostState(
    val post: UserFeedItem? = null,
    val comments: ImmutableList<String> = persistentListOf()
)

sealed class PostEffect {

    data class Error(@StringRes val message: Int) : PostEffect()
}