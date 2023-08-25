package me.cniekirk.mastodroid.data.repository

import androidx.datastore.core.DataStore
import me.cniekirk.mastodroid.data.model.response.RegisterClientResponse
import me.cniekirk.mastodroid.data.remote.MastodonService
import me.cniekirk.mastodroid.data.remote.Result
import me.cniekirk.mastodroid.data.remote.safeApiCall
import me.cniekirk.mastodroid.datastore.Preferences
import me.cniekirk.mastodroid.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val mastodonService: MastodonService,
    private val dataStore: DataStore<Preferences>
) : AuthenticationRepository {

    override suspend fun registerClient(serverUrl: String): Result<RegisterClientResponse> {
        return when (val response = safeApiCall { mastodonService.registerClient() }) {
            is Result.Failure -> {
                response
            }
            is Result.Success -> {
                dataStore.updateData {
                    it.toBuilder()
                        .setClientId(response.data.clientId)
                        .setClientSecret(response.data.clientSecret)
                        .build()
                }
                response
            }
        }
    }
}