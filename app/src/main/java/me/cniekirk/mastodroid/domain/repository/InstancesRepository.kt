package me.cniekirk.mastodroid.domain.repository

import me.cniekirk.mastodroid.data.model.response.ServerList
import me.cniekirk.mastodroid.data.remote.util.Result

interface InstancesRepository {

    suspend fun getInstances(): Result<ServerList>
}