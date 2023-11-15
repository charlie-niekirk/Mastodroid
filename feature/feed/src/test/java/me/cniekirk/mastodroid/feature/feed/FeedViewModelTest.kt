package me.cniekirk.mastodroid.feature.feed

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.cniekirk.domain.PostActionsUseCase
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.HomeFeedPagingSource
import me.cniekirk.mastodroid.core.model.AuthStatus
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test

class FeedViewModelTest {

    private val authenticationRepository: AuthenticationRepository = mockk()
    private val homeFeedPagingSource: HomeFeedPagingSource = mockk()
    private val postActionsUseCase: PostActionsUseCase = mockk()

    private lateinit var sut: FeedViewModel

    @Before
    fun setUp() {
        sut = FeedViewModel(authenticationRepository, homeFeedPagingSource, postActionsUseCase)
    }

    @Test
    fun `when checkAuthStatus returns Failure verify Error effect posted`() = runTest {
        // Given
        coEvery { authenticationRepository.getAuthStatus() } returns Result.Failure(error)

        sut.test(this, FeedState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectSideEffect(FeedEffect.Error(R.string.auth_status_error))
        }
    }

    @Test
    fun `when checkAuthStatus returns Success & LOGGED_IN verify viewState Success`() = runTest {
        // Given
        coEvery { authenticationRepository.getAuthStatus() } returns Result.Success(AuthStatus.LOGGED_IN)

        sut.test(this, FeedState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState { copy(viewState = ViewState.SUCCESS) }
        }
    }

    @Test
    fun `when checkAuthStatus returns Success & TOKEN_EXPIRED verify viewState Auth Error`() = runTest {
        // Given
        coEvery { authenticationRepository.getAuthStatus() } returns Result.Success(AuthStatus.TOKEN_EXPIRED)

        sut.test(this, FeedState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState { copy(viewState = ViewState.AUTH_ERROR) }
        }
    }

    @Test
    fun `when checkAuthStatus returns Success & NO_TOKEN verify viewState Auth Error`() = runTest {
        // Given
        coEvery { authenticationRepository.getAuthStatus() } returns Result.Success(AuthStatus.NO_TOKEN)

        sut.test(this, FeedState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState { copy(viewState = ViewState.AUTH_ERROR) }
        }
    }

    companion object {
        val error = UnknownError()
    }
}