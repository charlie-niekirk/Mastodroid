package me.cniekirk.mastodroid.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.cniekirk.mastodroid.core.database.dao.InstanceDao
import me.cniekirk.mastodroid.core.database.model.InstanceEntity

@Database(entities = [InstanceEntity::class], version = 1)
abstract class InstanceDatabase : RoomDatabase() {
    abstract fun instanceDao(): InstanceDao
}