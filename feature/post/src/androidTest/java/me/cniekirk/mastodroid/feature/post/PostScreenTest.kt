package me.cniekirk.mastodroid.feature.post

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.model.UserFeedItem
import org.junit.Rule
import org.junit.Test

class PostScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialState_RendersLoadingIndicator() {
        composeTestRule.setContent {
            MastodroidTheme {
                PostScreen(
                    state = testInitialPostState,
                    onBackPressed = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("post_loader").assertIsDisplayed()
        composeTestRule.onNodeWithTag("post_page_content").assertDoesNotExist()
    }

    companion object {
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

        val testInitialPostState = PostState()
    }
}