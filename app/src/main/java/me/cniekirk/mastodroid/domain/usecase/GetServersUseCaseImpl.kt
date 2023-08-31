package me.cniekirk.mastodroid.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.data.model.response.Instance
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.domain.model.UiInstance
import me.cniekirk.mastodroid.domain.repository.InstancesRepository
import javax.inject.Inject

class GetServersUseCaseImpl @Inject constructor(
    private val instancesRepository: InstancesRepository
) : GetServersUseCase {

    override suspend fun invoke(query: String): Result<ImmutableList<UiInstance>> {
        return when (val response = instancesRepository.getInstances()) {
            is Result.Failure -> response
            is Result.Success -> {
                Result.Success(response.data.instances?.filterNotNull()?.toUiInstances() ?: persistentListOf())
            }
        }
    }

    private fun List<Instance>.toUiInstances(): ImmutableList<UiInstance> {
        return this.map { instance ->
            UiInstance(name = instance.name ?: "", users = instance.users?.toIntOrNull() ?: 0, id = instance.id ?: "")
        }.toImmutableList()
    }
}