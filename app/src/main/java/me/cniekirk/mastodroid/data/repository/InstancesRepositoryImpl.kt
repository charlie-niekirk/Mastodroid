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

    override suspend fun getInstances(query: String): Result<ServerList> {
        return safeApiCall { instancesService.getInstances("Bearer $TOKEN", query = query) }
    }

    companion object {
        const val TOKEN = "kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}