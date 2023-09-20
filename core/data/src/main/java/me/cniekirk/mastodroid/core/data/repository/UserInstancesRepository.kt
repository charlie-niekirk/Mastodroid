package me.cniekirk.mastodroid.core.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.core.common.util.NetworkError
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.util.toInstanceEntity
import me.cniekirk.mastodroid.core.data.util.toMastodonInstance
import me.cniekirk.mastodroid.core.database.dao.InstanceDao
import me.cniekirk.mastodroid.core.model.MastodonInstance
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import javax.inject.Inject

class UserInstancesRepository @Inject constructor(
    private val mastodonNetworkDataSource: MastodroidNetworkDataSource,
    private val instancesDao: InstanceDao
) : InstancesRepository {

    override suspend fun getAllInstances(): Result<ImmutableList<MastodonInstance>> {
        val instances = instancesDao.getAll()
        if (instances.isEmpty()) {
            if (!syncInstances()) {
                return Result.Failure(NetworkError())
            }
        }

        val mappedInstances = instances.map { it.toMastodonInstance() }.toImmutableList()

        return Result.Success(mappedInstances)
    }

    private suspend fun syncInstances(): Boolean {
        return when (val response = mastodonNetworkDataSource.getInstanceList()) {
            is Result.Failure -> {
                false
            }
            is Result.Success -> {
                val instances = response.data.instances?.map { it.toInstanceEntity() }
                if (instances != null) {
                    instancesDao.insertAll(*instances.toTypedArray())
                }
                true
            }
        }
    }
}