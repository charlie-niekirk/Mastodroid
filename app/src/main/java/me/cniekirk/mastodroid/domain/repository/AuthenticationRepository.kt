package me.cniekirk.mastodroid.domain.repository

import me.cniekirk.mastodroid.data.model.response.RegisterClientResponse
import me.cniekirk.mastodroid.data.remote.Result

interface AuthenticationRepository {

    suspend fun registerClient(serverUrl: String): Result<RegisterClientResponse>
}