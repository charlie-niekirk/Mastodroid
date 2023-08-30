package me.cniekirk.mastodroid.features.home.login

import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cniekirk.mastodroid.domain.model.UiInstance

data class LoginState(
    val query: String = "",
    val servers: ImmutableList<UiInstance> = persistentListOf(),
    val pager: Flow<PagingData<UiInstance>> = flowOf()
)

sealed class LoginEffect {

    data object LoginSuccess : LoginEffect()
}