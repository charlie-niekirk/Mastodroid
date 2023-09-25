package me.cniekirk.mastodroid.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ServerConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "client_id") val clientId: String,
    @ColumnInfo(name = "client_secret") val clientSecret: String,
    @ColumnInfo(name = "server_url") val serverUrl: String,
    @ColumnInfo(name = "user_auth_token") val userAuthToken: String
)
