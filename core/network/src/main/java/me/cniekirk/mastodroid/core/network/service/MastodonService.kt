package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.network.model.NetworkCheckUserAuthResponse
import me.cniekirk.mastodroid.core.network.model.NetworkRegisterClientResponse
import me.cniekirk.mastodroid.core.network.model.NetworkStatus
import me.cniekirk.mastodroid.core.network.model.NetworkStatusContext
import me.cniekirk.mastodroid.core.network.model.NetworkUserTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MastodonService {

    @POST("api/v1/apps")
    @FormUrlEncoded
    suspend fun registerClient(
        @Field("client_name") clientName: String = "Mastodroid",
        @Field("redirect_uris") redirectUris: String = "mastodroid://verifycode",
        @Field("scopes") scopes: String = "read write follow push",
        @Field("website") website: String = "https://cniekirk.me"
    ): Response<NetworkRegisterClientResponse>

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun getUserToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("redirect_uri") redirectUri: String = "mastodroid://verifycode",
        @Field("scope") scope: String = "read write follow push"
    ): Response<NetworkUserTokenResponse>

    @GET("api/v1/accounts/verify_credentials")
    suspend fun checkUserAuth(
        @Header("Authorization") authorization: String
    ): Response<NetworkCheckUserAuthResponse>

    @GET("api/v1/timelines/home")
    suspend fun getUserFeed(
        @Header("Authorization") authorization: String,
        @Query("max_id") maxId: Long? = null
    ): Response<List<NetworkStatus>>

    @GET("api/v1/statuses/{id}")
    suspend fun getStatus(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<NetworkStatus>

    @GET("api/v1/statuses/{id}/context")
    suspend fun getStatusContext(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
    ): Response<NetworkStatusContext>

    @POST("api/v1/statuses/{id}/favourite")
    suspend fun favouriteStatus(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
    ): Response<NetworkStatus>

    @POST("api/v1/statuses/{id}/unfavourite")
    suspend fun undoFavouriteStatus(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
    ): Response<NetworkStatus>

    @POST("api/v1/statuses/{id}/reblog")
    @FormUrlEncoded
    suspend fun reblogStatus(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Field("visibility") visibility: String = "public"
    ): Response<NetworkStatus>
}