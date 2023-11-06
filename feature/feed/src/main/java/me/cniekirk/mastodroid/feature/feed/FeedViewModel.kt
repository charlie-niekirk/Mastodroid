package me.cniekirk.mastodroid.feature.feed

import android.text.Spanned
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.mapper.toUserFeedItem
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.HomeFeedPagingSource
import me.cniekirk.mastodroid.core.model.AuthStatus.LOGGED_IN
import me.cniekirk.mastodroid.core.model.AuthStatus.NO_TOKEN
import me.cniekirk.mastodroid.core.model.AuthStatus.TOKEN_EXPIRED
import me.cniekirk.mastodroid.core.ui.toAnnotatedString
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class FeedViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val homeFeedPagingSource: HomeFeedPagingSource
) : ViewModel(), ContainerHost<FeedState, FeedEffect> {

    override val container = container<FeedState, FeedEffect>(
        FeedState(
            feedItems = Pager(
                PagingConfig(pageSize = 20)
            ) {
                homeFeedPagingSource
            }.flow
                .map { pagingData ->
                    pagingData.map { networkStatus -> networkStatus.toUserFeedItem("") }
                }
                .cachedIn(viewModelScope)
        )
    ) {
        // Check if logged in
        checkAuthStatus()
    }

    private fun checkAuthStatus() = intent {
        // Stub for now
        when (val authCheckResponse = authenticationRepository.getAuthStatus()) {
            is Result.Failure -> {
                postSideEffect(FeedEffect.Error(R.string.auth_status_error))
            }
            is Result.Success -> {
                when (authCheckResponse.data) {
                    LOGGED_IN -> {
                        reduce { state.copy(viewState = ViewState.SUCCESS) }
                    }
                    TOKEN_EXPIRED, NO_TOKEN -> {
                        reduce { state.copy(viewState = ViewState.AUTH_ERROR) }
                    }
                }
            }
        }
    }
}