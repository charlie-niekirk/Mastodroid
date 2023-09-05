package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkServerList
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

    override suspend fun getInstanceList(): Result<NetworkServerList> =
        safeApiCall { instancesService.getInstances(authorization = TOKEN) }

    companion object {
        const val TOKEN = "Bearer kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}