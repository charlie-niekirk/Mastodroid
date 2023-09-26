package me.cniekirk.mastodroid.core.data.repository

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.model.AuthStatus

interface AuthenticationRepository {

    suspend fun getOauthUrl(): Result<String>

    suspend fun getAndPersistToken(code: String): Result<String>

    suspend fun serverSelected(serverUrl: String): Result<Boolean>

    suspend fun getAuthStatus(): Result<AuthStatus>
}