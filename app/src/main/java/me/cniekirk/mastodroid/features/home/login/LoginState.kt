package me.cniekirk.mastodroid.features.home.login

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.domain.model.UiInstance

data class LoginState(
    val query: String = "",
    val servers: ImmutableList<UiInstance> = persistentListOf()
)

sealed class LoginEffect {

    data object LoginSuccess : LoginEffect()
}