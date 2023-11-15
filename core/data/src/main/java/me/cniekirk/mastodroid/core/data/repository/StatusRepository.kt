package me.cniekirk.mastodroid.core.data.repository

import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.model.UserFeedItem

interface StatusRepository {

    fun getStatusFeed(): Flow<PagingData<UserFeedItem>>

    suspend fun getStatus(id: String): Result<UserFeedItem>

    suspend fun getStatusContext(id: String): Result<ImmutableList<UserFeedItem>>

    suspend fun favouriteStatus(id: String): Result<UserFeedItem>

    suspend fun undoFavouriteStatus(id: String): Result<UserFeedItem>

    suspend fun reblogStatus(id: String): Result<UserFeedItem>
}