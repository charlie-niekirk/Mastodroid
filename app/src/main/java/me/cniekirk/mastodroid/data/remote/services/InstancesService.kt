package me.cniekirk.mastodroid.data.remote.services

import me.cniekirk.mastodroid.data.model.response.ServerList
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
    ): Response<ServerList>

    @GET("api/1.0/instances/list")
    suspend fun getInstances(
        @Header("Authorization") authorization: String,
        @Query("count") count: Int = 25,
        @Query("sort_by") sortBy: String = "users",
        @Query("sort_order") sortOrder: String = "desc",
        @Query("min_id") minId: String?
    ): Response<ServerList>
}