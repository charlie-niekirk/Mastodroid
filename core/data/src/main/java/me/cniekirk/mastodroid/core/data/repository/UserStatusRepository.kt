package me.cniekirk.mastodroid.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.common.util.UnexpectedError
import me.cniekirk.mastodroid.core.data.mapper.toUserFeedItem
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.database.model.ServerConfigurationEntity
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import javax.inject.Inject

class UserStatusRepository @Inject constructor(
    private val mastodroidPreferencesDataSource: MastodroidPreferencesDataSource,
    private val serverConfigurationDao: ServerConfigurationDao,
    private val homeFeedPagingSource: HomeFeedPagingSource,
    private val mastodroidNetworkDataSource: MastodroidNetworkDataSource
) : StatusRepository {

    override fun getStatusFeed(): Flow<PagingData<UserFeedItem>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ) { homeFeedPagingSource }
            .flow
            .map { pagingData ->
                pagingData.map { networkStatus -> networkStatus.toUserFeedItem("") }
            }
    }

    override suspend fun getStatus(id: String): Result<UserFeedItem> =
        executeRequest { serverConfiguration ->
            when (val status = mastodroidNetworkDataSource.getStatus(serverConfiguration.userAuthToken, id)) {
                is Result.Failure -> status
                is Result.Success -> Result.Success(status.data.toUserFeedItem(""))
            }
        }

    override suspend fun getStatusContext(id: String): Result<ImmutableList<UserFeedItem>> =
        executeRequest { serverConfiguration ->
            when (val statusContextResponse = mastodroidNetworkDataSource.getStatusContext(id, serverConfiguration.userAuthToken)) {
                is Result.Failure -> statusContextResponse
                is Result.Success -> {
                    // Map data
                    val comments = statusContextResponse.data.descendants
                    Result.Success(
                        comments.map { comment ->
                            val replyUser = comments.firstOrNull { it.id?.equals(comment.inReplyToId) == true }
                            val replyText = if (replyUser != null) {
                                replyUser.account?.username ?: ""
                            } else ""
                            comment.toUserFeedItem(replyText)
                        }.toImmutableList()
                    )
                }
            }
        }

    override suspend fun favouriteStatus(id: String): Result<UserFeedItem> =
        executeRequest { serverConfiguration ->
            when (val favouriteResponse = mastodroidNetworkDataSource.favouriteStatus(id, serverConfiguration.userAuthToken)) {
                is Result.Failure -> favouriteResponse
                is Result.Success -> Result.Success(favouriteResponse.data.toUserFeedItem(""))
            }
        }

    override suspend fun undoFavouriteStatus(id: String): Result<UserFeedItem> =
        executeRequest { serverConfiguration ->
            when (val favouriteResponse = mastodroidNetworkDataSource.unfavouriteStatus(id, serverConfiguration.userAuthToken)) {
                is Result.Failure -> favouriteResponse
                is Result.Success -> Result.Success(favouriteResponse.data.toUserFeedItem(""))
            }
        }

    override suspend fun reblogStatus(id: String): Result<UserFeedItem> =
        executeRequest { serverConfiguration ->
            when (val reblogResponse = mastodroidNetworkDataSource.reblogStatus(id, serverConfiguration.userAuthToken)) {
                is Result.Failure -> reblogResponse
                is Result.Success -> Result.Success(reblogResponse.data.toUserFeedItem(""))
            }
        }

    private suspend fun <T> executeRequest(body: suspend (ServerConfigurationEntity) -> Result<T>): Result<T> {
        val selectedServerUid = mastodroidPreferencesDataSource.userData.first().selectedServerUid
        val serverConfiguration = serverConfigurationDao.findByUid(selectedServerUid).firstOrNull()
        return if (serverConfiguration != null) {
            body(serverConfiguration)
        } else {
            Result.Failure(UnexpectedError())
        }
    }
}