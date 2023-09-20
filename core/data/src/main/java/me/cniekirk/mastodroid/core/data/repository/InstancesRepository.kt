package me.cniekirk.mastodroid.core.data.repository

import kotlinx.collections.immutable.ImmutableList
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.model.MastodonInstance

interface InstancesRepository {

    suspend fun getAllInstances(): Result<ImmutableList<MastodonInstance>>
}