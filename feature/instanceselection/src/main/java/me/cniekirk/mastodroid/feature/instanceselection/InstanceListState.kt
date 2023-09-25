package me.cniekirk.mastodroid.feature.instanceselection

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.model.MastodonInstance

data class InstanceListState(
    val servers: ImmutableList<MastodonInstance> = persistentListOf(),
    val query: String = ""
)

sealed class InstanceListEffect {

    data class InstanceSelectedLogin(val oauthUrl: String) : InstanceListEffect()

    data class ShowError(@StringRes val message: Int) : InstanceListEffect()
}