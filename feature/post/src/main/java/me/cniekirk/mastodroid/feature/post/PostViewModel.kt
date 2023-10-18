package me.cniekirk.mastodroid.feature.post

import android.text.Spanned
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.ui.toAnnotatedString
import me.cniekirk.mastodroid.feature.post.navigation.PostArgs
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val statusRepository: StatusRepository
) : ViewModel(), ContainerHost<PostState, PostEffect> {

    private val postArgs = PostArgs(savedStateHandle)

    override val container = container<PostState, PostEffect>(PostState()) {
        loadPostData(postArgs.postId)
    }

    private fun loadPostData(postId: String) = intent {
        when (val statusAndContext = statusRepository.getStatus(postId)) {
            is Result.Failure -> {
                // Post error
                reduce { state.copy(isLoading = false) }
                postSideEffect(PostEffect.Error(R.string.post_load_error))
            }
            is Result.Success -> {
                // Reduce state
                reduce {
                    val post = statusAndContext.data
                    state.copy(
                        isLoading = false,
                        post = post.copy(
                            content = (post.content as Spanned).toAnnotatedString(Color.Blue)
                        )
                    )
                }

                // Then get the comments
                getStatusContext(postId)
            }
        }
    }

    private fun getStatusContext(postId: String) = intent {
        when (val statusAndContext = statusRepository.getStatusContext(postId)) {
            is Result.Failure -> {
                // Post error
                reduce { state.copy(isLoading = false) }
                postSideEffect(PostEffect.Error(R.string.post_load_error))
            }
            is Result.Success -> {
                // Reduce state
                reduce {
                    val comments = statusAndContext.data
                    state.copy(
                        areCommentsLoading = false,
                        comments = comments.map { it.copy(content = (it.content as Spanned).toAnnotatedString(Color.Blue)) }.toImmutableList()
                    )
                }
            }
        }
    }
}