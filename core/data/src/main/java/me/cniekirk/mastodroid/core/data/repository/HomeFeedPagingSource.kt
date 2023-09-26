package me.cniekirk.mastodroid.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.database.model.ServerConfigurationEntity
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import me.cniekirk.mastodroid.core.network.model.NetworkStatus
import timber.log.Timber
import javax.inject.Inject

class HomeFeedPagingSource @Inject constructor(
    private val mastodroidNetworkDataSource: MastodroidNetworkDataSource,
    private val mastodroidPreferencesDataSource: MastodroidPreferencesDataSource,
    private val serverConfigurationDao: ServerConfigurationDao
): PagingSource<Long, NetworkStatus>() {

    private var serverConfiguration: ServerConfigurationEntity? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, NetworkStatus> {
        withContext(Dispatchers.IO) {
            if (serverConfiguration == null) {
                val selectedServerId = mastodroidPreferencesDataSource.userData.first().selectedServerUid
                val config = serverConfigurationDao.findByUid(selectedServerId).firstOrNull()
                if (config != null) {
                    serverConfiguration = config
                }
            }
        }

        return try {
            serverConfiguration?.let { config ->
                val nextId = params.key

                return when (val statuses = mastodroidNetworkDataSource.getUserFeed(config.userAuthToken, nextId)) {
                    is Result.Failure -> LoadResult.Error(statuses.error)
                    is Result.Success -> return LoadResult.Page(
                        data = statuses.data,
                        prevKey = null,
                        nextKey = statuses.data.last().id?.toLong()
                    )
                }
            } ?: run {
                LoadResult.Error(Exception("Server config was null"))
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, NetworkStatus>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id?.toLong()
        }
    }
}