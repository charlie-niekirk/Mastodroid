package me.cniekirk.mastodroid.core.network

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.network.model.NetworkCheckUserAuthResponse
import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkServerList
import me.cniekirk.mastodroid.core.network.model.NetworkStatus
import me.cniekirk.mastodroid.core.network.model.NetworkUserTokenResponse

interface MastodroidNetworkDataSource {

    suspend fun registerClient(instanceUrl: String): Result<NetworkRegisterClientResponse>

    suspend fun getToken(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<NetworkUserTokenResponse>

    suspend fun getInstanceList(): Result<NetworkServerList>

    suspend fun checkUserAuth(token: String): Result<NetworkCheckUserAuthResponse>

    suspend fun getUserFeed(
        token: String,
        maxId: Long? = null
    ): Result<List<NetworkStatus>>
}