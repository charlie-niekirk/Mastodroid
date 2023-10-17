package me.cniekirk.mastodroid.core.data.repository

import android.text.Spanned
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.common.util.UnexpectedError
import me.cniekirk.mastodroid.core.data.mapper.toUserFeedItem
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource
import me.cniekirk.mastodroid.core.model.UserStatusAndContext
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import javax.inject.Inject

class UserStatusRepository @Inject constructor(
    private val mastodroidPreferencesDataSource: MastodroidPreferencesDataSource,
    private val serverConfigurationDao: ServerConfigurationDao,
    private val mastodroidNetworkDataSource: MastodroidNetworkDataSource
) : StatusRepository {

    override suspend fun getStatusAndContext(id: String): Result<UserStatusAndContext> {
        val selectedServerUid = mastodroidPreferencesDataSource.userData.first().selectedServerUid
        val serverConfiguration = serverConfigurationDao.findByUid(selectedServerUid).firstOrNull()
        return if (serverConfiguration != null) {
            when (val status = mastodroidNetworkDataSource.getStatus(serverConfiguration.userAuthToken, id)) {
                is Result.Failure -> status
                is Result.Success -> {
                    when (val statusContextResponse = mastodroidNetworkDataSource.getStatusContext(id, serverConfiguration.userAuthToken)) {
                        is Result.Failure -> statusContextResponse
                        is Result.Success -> {
                            // Map data
                            Result.Success(
                                UserStatusAndContext(
                                    status.data.toUserFeedItem(),
                                    statusContextResponse.data.descendants.map { it.toUserFeedItem() }.toImmutableList()
                                )
                            )
                        }
                    }
                }
            }
        } else {
            Result.Failure(UnexpectedError())
        }
    }
}