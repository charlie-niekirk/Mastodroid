package me.cniekirk.mastodroid.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.data.model.response.ServerList
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.domain.model.UiInstance
import me.cniekirk.mastodroid.domain.repository.InstancesRepository
import javax.inject.Inject

class SearchServersUseCaseImpl @Inject constructor(
    private val instancesRepository: InstancesRepository
) : SearchServersUseCase {

    override suspend fun invoke(query: String): Result<ImmutableList<UiInstance>> {
        return when (val response = instancesRepository.getInstances(query)) {
            is Result.Failure -> response
            is Result.Success -> {
                Result.Success(response.data.toUiInstances())
            }
        }
    }

    private fun ServerList.toUiInstances(): ImmutableList<UiInstance> {
        val instances = this.instances?.map { instance ->
            UiInstance(
                instance?.name ?: "",
                instance?.activeUsers ?: 0,
                instance?.id ?: ""
            )
        }
        return if (instances.isNullOrEmpty()) {
            persistentListOf()
        } else {
            instances.toImmutableList()
        }
    }
}