package me.cniekirk.mastodroid.data.repository

import me.cniekirk.mastodroid.data.model.response.ServerList
import me.cniekirk.mastodroid.data.remote.services.InstancesService
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.data.remote.util.safeApiCall
import me.cniekirk.mastodroid.domain.repository.InstancesRepository
import javax.inject.Inject

class InstancesRepositoryImpl @Inject constructor(
    private val instancesService: InstancesService
) : InstancesRepository {

    override suspend fun getInstances(): Result<ServerList> {
        return safeApiCall { instancesService.getInstances("Bearer $TOKEN", count = 1000) }
    }

    companion object {
        const val TOKEN = "kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}