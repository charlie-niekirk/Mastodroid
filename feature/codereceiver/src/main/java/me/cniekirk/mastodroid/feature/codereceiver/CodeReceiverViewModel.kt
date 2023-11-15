package me.cniekirk.mastodroid.feature.codereceiver

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.UserAuthenticationRepository
import me.cniekirk.mastodroid.feature.codereceiver.navigation.CodeReceiverArgs
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CodeReceiverViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel(), ContainerHost<CodeReceiverState, CodeReceiverEffect> {

    private val codeReceiverArgs = CodeReceiverArgs(savedStateHandle)

    override val container = container<CodeReceiverState, CodeReceiverEffect>(CodeReceiverState()) {
        // Get and persist token here
        getAndPersistAccessToken()
    }

    private fun getAndPersistAccessToken() = intent {
        when (val response = authenticationRepository.getAndPersistToken(codeReceiverArgs.code)) {
            is Result.Failure -> {
                // Post error effect
                Timber.e(response.error)
            }
            is Result.Success -> {
                postSideEffect(CodeReceiverEffect.Success)
            }
        }
    }
}