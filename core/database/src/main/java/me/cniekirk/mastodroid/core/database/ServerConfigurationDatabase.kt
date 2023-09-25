package me.cniekirk.mastodroid.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import me.cniekirk.mastodroid.core.database.model.ServerConfigurationEntity

@Database(entities = [ServerConfigurationEntity::class], version = 1)
abstract class ServerConfigurationDatabase : RoomDatabase() {
    abstract fun serverConfigurationDao(): ServerConfigurationDao
}