package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkUserTokenResponse
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

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun getUserToken(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("redirect_uri") redirectUri: String = "https://verifymastodroidcode.com",
        @Field("scope") scope: String = "read write follow push",
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<NetworkUserTokenResponse>
}