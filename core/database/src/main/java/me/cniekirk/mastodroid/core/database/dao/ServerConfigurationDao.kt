package me.cniekirk.mastodroid.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.cniekirk.mastodroid.core.database.model.ServerConfigurationEntity

@Dao
interface ServerConfigurationDao {

    @Query("SELECT * FROM serverConfigurationEntity")
    fun getAll(): List<ServerConfigurationEntity>

    @Query("SELECT * FROM serverConfigurationEntity WHERE uid LIKE '%' || :uid || '%'")
    fun findByUid(uid: Long): List<ServerConfigurationEntity>

    @Insert
    fun insertOne(serverConfiguration: ServerConfigurationEntity): Long

    @Insert
    fun insertAll(vararg serverConfigurations: ServerConfigurationEntity): Array<Long>

    @Delete
    fun delete(serverConfiguration: ServerConfigurationEntity)
}