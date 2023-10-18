package me.cniekirk.mastodroid.core.data.repository

import kotlinx.collections.immutable.ImmutableList
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.model.UserFeedItem

interface StatusRepository {

    suspend fun getStatus(id: String): Result<UserFeedItem>

    suspend fun getStatusContext(id: String): Result<ImmutableList<UserFeedItem>>
}