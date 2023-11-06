package me.cniekirk.mastodroid.core.model

sealed class PostAction {

    data class Favourite(val id: String) : PostAction()

    data class Unfavourite(val id: String) : PostAction()

    data class Reblog(val id: String) : PostAction()
}