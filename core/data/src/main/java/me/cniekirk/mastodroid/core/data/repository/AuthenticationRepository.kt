package me.cniekirk.mastodroid.core.data.repository

import me.cniekirk.mastodroid.core.common.util.Result

interface AuthenticationRepository {

    suspend fun getOauthUrl(): Result<String>

    suspend fun serverSelected(serverUrl: String): Result<Boolean>
}