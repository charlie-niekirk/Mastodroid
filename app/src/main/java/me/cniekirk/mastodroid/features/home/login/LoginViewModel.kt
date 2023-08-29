package me.cniekirk.mastodroid.features.home.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.domain.usecase.SearchServersUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val searchServersUseCase: SearchServersUseCase
) : ViewModel(), ContainerHost<LoginState, LoginEffect> {

    override val container = container<LoginState, LoginEffect>(LoginState())

    @OptIn(OrbitExperimental::class)
    fun queryChanged(query: String) = blockingIntent {
        reduce { state.copy(query = query) }
        queryServers(query)
    }

    private fun queryServers(query: String) = intent {
        when (val response = searchServersUseCase(query)) {
            is Result.Failure -> {
                // Post some error
            }
            is Result.Success -> {
                reduce { state.copy(servers = response.data) }
            }
        }
    }

    fun onServerSelected(id: Int) = intent {

    }
}