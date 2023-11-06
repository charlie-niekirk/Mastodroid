package me.cniekirk.domain

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.PostAction
import me.cniekirk.mastodroid.core.model.UserFeedItem
import javax.inject.Inject

/**
 * Performing actions on posts happens on multiple screens, therefore extract
 * logic to usecase class to avoid duplication
 */
class PostActionsUseCase @Inject constructor(
    private val statusRepository: StatusRepository
) {

    suspend operator fun invoke(postAction: PostAction): Result<UserFeedItem> {
        return when (postAction) {
            is PostAction.Favourite -> statusRepository.favouriteStatus(postAction.id)
            is PostAction.Reblog -> statusRepository.reblogStatus(postAction.id)
            is PostAction.Unfavourite -> statusRepository.undoFavouriteStatus(postAction.id)
        }
    }
}