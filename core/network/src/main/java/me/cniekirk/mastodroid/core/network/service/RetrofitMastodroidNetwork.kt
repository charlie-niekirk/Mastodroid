package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import me.cniekirk.mastodroid.core.network.model.NetworkCheckUserAuthResponse
import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkServerList
import me.cniekirk.mastodroid.core.network.model.NetworkStatus
import me.cniekirk.mastodroid.core.network.model.NetworkStatusContext
import me.cniekirk.mastodroid.core.network.model.NetworkUserTokenResponse
import me.cniekirk.mastodroid.core.network.util.HostSelectionInterceptor
import me.cniekirk.mastodroid.core.network.util.safeApiCall
import javax.inject.Inject

class RetrofitMastodroidNetwork @Inject constructor(
    private val instancesService: InstancesService,
    private val mastodonService: MastodonService,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : MastodroidNetworkDataSource {

    override suspend fun registerClient(instanceUrl: String): Result<NetworkRegisterClientResponse> {
        hostSelectionInterceptor.setHost(instanceUrl)
        return safeApiCall { mastodonService.registerClient() }
    }

    override suspend fun getToken(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<NetworkUserTokenResponse> {
        return safeApiCall { mastodonService.getUserToken(code, clientId, clientSecret) }
    }

    override suspend fun getInstanceList(): Result<NetworkServerList> =
        safeApiCall { instancesService.getInstances(authorization = INSTANCES_TOKEN) }

    override suspend fun checkUserAuth(token: String): Result<NetworkCheckUserAuthResponse> =
        safeApiCall { mastodonService.checkUserAuth("Bearer $token") }

    override suspend fun getUserFeed(token: String, maxId: Long?): Result<List<NetworkStatus>> =
        safeApiCall { mastodonService.getUserFeed("Bearer $token", maxId) }

    override suspend fun getStatus(token: String, id: String): Result<NetworkStatus> =
        safeApiCall { mastodonService.getStatus(token, id) }

    override suspend fun getStatusContext(id: String, token: String): Result<NetworkStatusContext> =
        safeApiCall { mastodonService.getStatusContext(id, "Bearer $token") }

    override suspend fun favouriteStatus(id: String, token: String): Result<NetworkStatus> =
        safeApiCall { mastodonService.favouriteStatus(id, "Bearer $token") }

    override suspend fun unfavouriteStatus(id: String, token: String): Result<NetworkStatus> =
        safeApiCall { mastodonService.undoFavouriteStatus(id, "Bearer $token") }

    override suspend fun reblogStatus(id: String, token: String): Result<NetworkStatus> =
        safeApiCall { mastodonService.reblogStatus(id, "Bearer $token") }

    companion object {
        const val INSTANCES_TOKEN = "Bearer kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}