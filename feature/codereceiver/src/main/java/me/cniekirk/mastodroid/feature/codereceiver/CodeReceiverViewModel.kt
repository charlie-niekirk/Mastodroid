package me.cniekirk.mastodroid.feature.codereceiver

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.feature.codereceiver.navigation.CodeReceiverArgs
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CodeReceiverViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<CodeReceiverState, CodeReceiverEffect> {

    private val codeReceiverArgs = CodeReceiverArgs(savedStateHandle)

    override val container = container<CodeReceiverState, CodeReceiverEffect>(CodeReceiverState()) {
        // Get and persist token here
    }

    private fun getAndPersistAccessToken() = intent {
        val code = codeReceiverArgs.code
    }
}