package me.cniekirk.mastodroid.features.home.login

import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.orbitmvi.orbit.test.test

class LoginViewModelTest {

    private val viewModel = LoginViewModel()

    @BeforeEach
    fun setUp() {
        // Init mocks
    }

    @Test
    fun `when queryChanged invoked verify state reduced with correct query`() = runTest {
        viewModel.test(this) {
            expectInitialState()

            invokeIntent {
                queryChanged(TEST_QUERY)
                // Temporarily needed when testing blockingIntent
                return@invokeIntent Job()
            }

            expectState { copy(query = TEST_QUERY) }
        }
    }

    companion object {
        const val TEST_QUERY = "test"
    }
}