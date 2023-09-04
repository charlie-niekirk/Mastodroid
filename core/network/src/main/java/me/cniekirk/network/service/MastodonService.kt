package me.cniekirk.network.service

import me.cniekirk.mastodroid.data.model.response.RegisterClientResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MastodonService {

    @POST("api/v1/apps")
    @FormUrlEncoded
    suspend fun registerClient(
        @Field("client_name") clientName: String = "Mastodroid",
        @Field("redirect_uris") redirectUris: String = "urn:ietf:wg:oauth:2.0:oob",
        @Field("scopes") scopes: String = "read write follow push",
        @Field("website") website: String = "https://cniekirk.me"
    ): Response<RegisterClientResponse>
}