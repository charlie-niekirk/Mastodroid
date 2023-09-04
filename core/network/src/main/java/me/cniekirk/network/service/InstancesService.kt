package me.cniekirk.network.service

import me.cniekirk.network.model.NetworkServerList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface InstancesService {

    @GET("api/1.0/instances/search")
    suspend fun getInstances(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int = 100,
        @Query("q") query: String
    ): Response<NetworkServerList>

    @GET("api/1.0/instances/list")
    suspend fun getInstances(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int = 25,
        @Query("sort_by") sortBy: String = "users",
        @Query("sort_order") sortOrder: String = "desc"
    ): Response<NetworkServerList>
}