package me.cniekirk.mastodroid.core.network

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkServerList

interface MastodroidNetworkDataSource {

    suspend fun registerClient(instanceUrl: String): Result<NetworkRegisterClientResponse>

    suspend fun getInstanceList(): Result<NetworkServerList>
}