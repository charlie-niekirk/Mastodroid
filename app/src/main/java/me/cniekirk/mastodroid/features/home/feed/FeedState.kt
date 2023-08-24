package me.cniekirk.mastodroid.features.home.feed

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FeedState(
    val feedItems: ImmutableList<String> = persistentListOf()
)

sealed class FeedEffect {

    data class Error(@StringRes val message: Int) : FeedEffect()
}