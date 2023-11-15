package me.cniekirk.mastodroid.feature.post

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import me.cniekirk.domain.PostActionsUseCase
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.UserFeedItem
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test

class PostViewModelTest {

    private val statusRepository: StatusRepository = mockk()
    private val postActionsUseCase: PostActionsUseCase = mockk()

    private lateinit var sut: PostViewModel

    @Before
    fun setUp() {
        val savedState = SavedStateHandle(mapOf("postId" to POST_ID))
        sut = PostViewModel(savedState, statusRepository, postActionsUseCase)
    }

    @Test
    fun `when loadPostData returns Failure verify loading set to false and Error effect posted`() = runTest {
        // Given
        coEvery { statusRepository.getStatus(any()) } returns Result.Failure(error)

        sut.test(this, PostState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState { copy(isLoading = false) }
            expectSideEffect(PostEffect.Error(R.string.post_load_error))
            coVerify(exactly = 1) { statusRepository.getStatus(POST_ID) }
            coVerify(exactly = 0) { statusRepository.getStatusContext(any()) }
        }
    }

    @Test
    fun `when loadPostData returns Success verify correct state & effects posted`() = runTest {
        // Given
        val comments = persistentListOf(feedItem)
        coEvery { statusRepository.getStatus(any()) } returns Result.Success(feedItem)
        coEvery { statusRepository.getStatusContext(any()) } returns Result.Success(comments)

        sut.test(this, PostState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState {
                copy(
                    isLoading = false,
                    post = feedItem.copy(
                        content = feedItem.content
                    )
                )
            }

            expectState {
                copy(
                    areCommentsLoading = false,
                    comments = comments
                )
            }

            coVerifySequence {
                statusRepository.getStatus(POST_ID)
                statusRepository.getStatusContext(POST_ID)
            }
        }
    }

    @Test
    fun `when loadPostData returns Success & getStatusContext returns Failure verify correct state & effects posted`() = runTest {
        // Given
        coEvery { statusRepository.getStatus(any()) } returns Result.Success(feedItem)
        coEvery { statusRepository.getStatusContext(any()) } returns Result.Failure(error)

        sut.test(this, PostState()) {
            expectInitialState()

            // When
            runOnCreate()

            // Then
            expectState {
                copy(
                    isLoading = false,
                    post = feedItem.copy(
                        content = feedItem.content
                    )
                )
            }

            expectState { copy(areCommentsLoading = false) }
            expectSideEffect(PostEffect.Error(R.string.post_load_error))

            coVerifySequence {
                statusRepository.getStatus(POST_ID)
                statusRepository.getStatusContext(POST_ID)
            }
        }
    }

    companion object {
        const val POST_ID = "1"

        val feedItem = UserFeedItem(
            1,
            "",
            "Example User",
            "example",
            "",
            "1hr",
            "10 October 2023 10:34",
            "text",
            11,
            12,
            13,
            "Mastodroid for Android",
            false,
            false,
            persistentListOf(),
            "someuser"
        )

        val error = UnknownError()
    }
}