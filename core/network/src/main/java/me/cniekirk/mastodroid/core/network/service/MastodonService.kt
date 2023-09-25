package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MastodonService {

    @POST("api/v1/apps")
    @FormUrlEncoded
    suspend fun registerClient(
        @Field("client_name") clientName: String = "Mastodroid",
        @Field("redirect_uris") redirectUris: String = "https://verifymastodroidcode.com",
        @Field("scopes") scopes: String = "read write follow push",
        @Field("website") website: String = "https://cniekirk.me"
    ): Response<NetworkRegisterClientResponse>
}