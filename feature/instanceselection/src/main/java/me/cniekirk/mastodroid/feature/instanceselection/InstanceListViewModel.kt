package me.cniekirk.mastodroid.feature.instanceselection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.InstancesRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class InstanceListViewModel @Inject constructor(
    private val instancesRepository: InstancesRepository
) : ViewModel(), ContainerHost<InstanceListState, InstanceListEffect> {

    override val container = container<InstanceListState, InstanceListEffect>(InstanceListState()) {
        getInstances()
    }

    @OptIn(OrbitExperimental::class)
    fun queryChanged(query: String) = blockingIntent {
        reduce { state.copy(query = query) }
    }

    private fun getInstances() = intent {
        when (val response = instancesRepository.getAllInstances()) {
            is Result.Failure -> {
                // Show error
                postSideEffect(InstanceListEffect.ShowError(R.string.instances_error))
            }
            is Result.Success -> {
                reduce { state.copy(servers = response.data) }
            }
        }
    }
}