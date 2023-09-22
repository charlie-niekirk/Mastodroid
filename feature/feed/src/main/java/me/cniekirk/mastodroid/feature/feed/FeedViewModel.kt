package me.cniekirk.mastodroid.feature.feed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class FeedViewModel @Inject constructor(

) : ViewModel(), ContainerHost<FeedState, FeedEffect> {

    override val container = container<FeedState, FeedEffect>(FeedState()) {
        // Check if logged in
        checkAuthStatus()
    }

    private fun checkAuthStatus() = intent {
        // Stub for now
        reduce { state.copy(viewState = ViewState.AUTH_ERROR) }
    }
}