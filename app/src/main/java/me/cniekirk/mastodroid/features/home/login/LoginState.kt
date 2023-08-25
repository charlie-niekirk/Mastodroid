package me.cniekirk.mastodroid.features.home.login

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class LoginState(
    val query: String = "",
    val servers: ImmutableList<String> = persistentListOf()
)

sealed class LoginEffect {

    data object LoginSuccess : LoginEffect()
}