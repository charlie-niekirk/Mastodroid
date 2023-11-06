package me.cniekirk.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.PostAction
import me.cniekirk.mastodroid.core.model.UserFeedItem
import org.junit.Before
import org.junit.Test

class PostActionsUseCaseTest {

    private val statusRepository: StatusRepository = mockk()
    private lateinit var sut: PostActionsUseCase

    @Before
    fun setUp() {
        sut = PostActionsUseCase(statusRepository)
    }

    @Test
    fun `favourite postAction executes favourite repository function`() = runTest {
        // Given
        coEvery { statusRepository.favouriteStatus(any()) } returns Result.Success(feedItem)

        // When
        sut.invoke(PostAction.Favourite(POST_ID))

        // Then
        coVerify { statusRepository.favouriteStatus(POST_ID) }
    }

    @Test
    fun `unfavourite postAction executes unfavourite repository function`() = runTest {
        // Given
        coEvery { statusRepository.undoFavouriteStatus(any()) } returns Result.Success(feedItem)

        // When
        sut.invoke(PostAction.Unfavourite(POST_ID))

        // Then
        coVerify { statusRepository.undoFavouriteStatus(POST_ID) }
    }

    @Test
    fun `reblog postAction executes reblog repository function`() = runTest {
        // Given
        coEvery { statusRepository.reblogStatus(any()) } returns Result.Success(feedItem)

        // When
        sut.invoke(PostAction.Reblog(POST_ID))

        // Then
        coVerify { statusRepository.reblogStatus(POST_ID) }
    }

    companion object {
        const val POST_ID = "1"
        val feedItem = UserFeedItem(
            1,
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
    }
}