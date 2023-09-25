package me.cniekirk.mastodroid.feature.instanceselection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.InstancesRepository
import me.cniekirk.mastodroid.core.model.MastodonInstance
import me.cniekirk.mastodroid.feature.instanceselection.navigation.InstanceListArgs
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InstanceListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val instancesRepository: InstancesRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel(), ContainerHost<InstanceListState, InstanceListEffect> {

    private val instanceListArgs = InstanceListArgs(savedStateHandle)

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

    fun onInstanceSelected(mastodonInstance: MastodonInstance) = intent {
        if (instanceListArgs.isLogin) {
            when (authenticationRepository.serverSelected(mastodonInstance.name)) {
                is Result.Failure -> {
                    // Post error effect
                    Timber.e("ERROR INSTANCE 1")
                }
                is Result.Success -> {
                    when (val urlResponse = authenticationRepository.getOauthUrl()) {
                        is Result.Failure -> {
                            // Post error effect
                            Timber.e("ERROR INSTANCE 2")
                        }
                        is Result.Success -> {
                            postSideEffect(InstanceListEffect.InstanceSelectedLogin(urlResponse.data))
                        }
                    }
                }
            }
        }
    }
}