package me.cniekirk.mastodroid.core.data.repository

import kotlinx.coroutines.flow.first
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.common.util.UnexpectedError
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.database.model.ServerConfigurationEntity
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import me.cniekirk.mastodroid.core.network.util.safeApiCall
import timber.log.Timber
import javax.inject.Inject

class UserAuthenticationRepository @Inject constructor(
    private val mastodroidPreferencesDataSource: MastodroidPreferencesDataSource,
    private val serverConfigurationDao: ServerConfigurationDao,
    private val mastodroidNetworkDataSource: MastodroidNetworkDataSource
) : AuthenticationRepository {

    override suspend fun getOauthUrl(): Result<String> {
        val selectedServerUid = mastodroidPreferencesDataSource.userData.first().selectedServerUid
        val serverConfiguration = serverConfigurationDao.findByUid(selectedServerUid).firstOrNull()
        return if (serverConfiguration != null) {
            Result.Success("https://${serverConfiguration.serverUrl}/$OAUTH_URL_PREFIX=${serverConfiguration.clientId}")
        } else {
            Result.Failure(UnexpectedError())
        }
    }

    override suspend fun serverSelected(serverUrl: String): Result<Boolean> {
        return when (val response = mastodroidNetworkDataSource.registerClient(serverUrl)) {
            is Result.Failure -> response
            is Result.Success -> {
                val initialServerConfig = ServerConfigurationEntity(
                    clientId = response.data.clientId,
                    clientSecret = response.data.clientSecret,
                    serverUrl = serverUrl,
                    userAuthToken = ""
                )
                val id = serverConfigurationDao.insertOne(initialServerConfig)
                mastodroidPreferencesDataSource.updateSelectedServerUid(id)
                Result.Success(true)
            }
        }
    }

    companion object {
        const val OAUTH_URL_PREFIX =
            "oauth/authorize?response_type=code&redirect_uri=https://verifymastodroidcode.com&scope=read+write+follow+push&client_id"
    }
}