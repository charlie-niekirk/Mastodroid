package me.cniekirk.mastodroid.core.network.service

import me.cniekirk.mastodroid.core.network.model.NetworkServerList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface InstancesService {

    @GET("api/1.0/instances/list")
    suspend fun getInstances(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int = 500,
        @Query("sort_by") sortBy: String = "users",
        @Query("sort_order") sortOrder: String = "desc"
    ): Response<NetworkServerList>
}