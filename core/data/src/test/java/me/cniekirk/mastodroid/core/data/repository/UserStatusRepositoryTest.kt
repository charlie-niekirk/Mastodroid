package me.cniekirk.mastodroid.core.data.repository

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.common.util.UnexpectedError
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource
import me.cniekirk.mastodroid.core.model.UserData
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UserStatusRepositoryTest {

    private val mastodroidPreferencesDataSource: MastodroidPreferencesDataSource = mockk()
    private val serverConfigurationDao: ServerConfigurationDao = mockk()
    private val homeFeedPagingSource: HomeFeedPagingSource = mockk()
    private val mastodroidNetworkDataSource: MastodroidNetworkDataSource = mockk()

    private lateinit var sut: UserStatusRepository

    @Before
    fun setUp() {
        sut = UserStatusRepository(
            mastodroidPreferencesDataSource,
            serverConfigurationDao,
            homeFeedPagingSource,
            mastodroidNetworkDataSource
        )
    }

    @Test
    fun `when getStatus returns Success verify mapped data returned`() = runTest {

    }

    @Test
    fun `when getStatus returns Failure verify mapped data returned`() = runTest {

    }

    @Test
    fun `when serverConfiguration returns null verify Failure returned from getStatus`() = runTest {
        // Given
        coEvery { mastodroidPreferencesDataSource.userData } returns userData
        every { serverConfigurationDao.findByUid(any()).firstOrNull() } returns null

        // When
        val statusResponse = sut.getStatus(STATUS_ID)

        // Then
        assertTrue(
            statusResponse is Result.Failure
                    && statusResponse.error is UnexpectedError
        )
    }

    companion object {
        const val STATUS_ID = "123456"
        const val SELECTED_SERVER_UID = 123L

        val userData = flowOf(UserData(SELECTED_SERVER_UID, true))
    }
}