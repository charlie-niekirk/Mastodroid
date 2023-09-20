package me.cniekirk.mastodroid.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [me.cniekirk.mastodroid.core.database.model.InstanceEntity::class], version = 1)
abstract class InstanceDatabase : RoomDatabase() {
    abstract fun instanceDao(): me.cniekirk.mastodroid.core.database.dao.InstanceDao
}