package me.cniekirk.mastodroid.features.home.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(

) : ViewModel(), ContainerHost<LoginState, LoginEffect> {

    override val container = container<LoginState, LoginEffect>(LoginState())

    @OptIn(OrbitExperimental::class)
    fun queryChanged(query: String) = blockingIntent {
        reduce { state.copy(query = query) }
        queryServers(query)
    }

    private fun queryServers(query: String) = intent {

    }

    fun onServerSelected(id: Int) = intent {

    }
}