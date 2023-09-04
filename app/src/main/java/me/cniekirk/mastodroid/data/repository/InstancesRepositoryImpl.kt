package me.cniekirk.mastodroid.data.repository

import me.cniekirk.mastodroid.data.local.db.InstanceDao
import me.cniekirk.mastodroid.data.local.db.InstanceEntity
import me.cniekirk.network.service.InstancesService
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.data.remote.util.safeApiCall
import me.cniekirk.mastodroid.domain.repository.InstancesRepository
import javax.inject.Inject

class InstancesRepositoryImpl @Inject constructor(
    private val instancesService: me.cniekirk.network.service.InstancesService,
    private val instanceDao: InstanceDao
) : InstancesRepository {

    override suspend fun getAllInstances(): Result<InstanceEntity> {
        var instances = instanceDao.getAll()
        if (instances.isEmpty()) {
            val response = safeApiCall { instancesService.getInstances("Bearer $TOKEN", count = 1000) }

        }
        return Result.Success(instances)
    }

    companion object {
        const val TOKEN = "kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}