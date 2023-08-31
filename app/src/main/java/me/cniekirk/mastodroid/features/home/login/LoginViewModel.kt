package me.cniekirk.mastodroid.features.home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.cniekirk.mastodroid.data.model.response.Instance
import me.cniekirk.mastodroid.data.remote.paging.InstancesPagingSource
import me.cniekirk.mastodroid.domain.model.UiInstance
import me.cniekirk.mastodroid.domain.usecase.GetServersUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getServersUseCase: GetServersUseCase
) : ViewModel(), ContainerHost<LoginState, LoginEffect> {

    override val container = container<LoginState, LoginEffect>(LoginState()) {
        getServers()
    }

    @OptIn(OrbitExperimental::class)
    fun queryChanged(query: String) = blockingIntent {
        reduce { state.copy(query = query) }

    }

    private fun getServers(query: String) = intent {

    }

    fun onServerSelected(id: Int) = intent {

    }
}