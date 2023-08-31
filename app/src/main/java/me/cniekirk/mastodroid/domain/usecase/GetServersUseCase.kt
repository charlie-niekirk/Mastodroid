package me.cniekirk.mastodroid.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import me.cniekirk.mastodroid.data.remote.util.Result
import me.cniekirk.mastodroid.domain.model.UiInstance

interface GetServersUseCase {

    suspend operator fun invoke(query: String): Result<ImmutableList<UiInstance>>
}