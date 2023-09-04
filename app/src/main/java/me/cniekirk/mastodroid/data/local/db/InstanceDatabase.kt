package me.cniekirk.mastodroid.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InstanceEntity::class], version = 1)
abstract class InstanceDatabase : RoomDatabase() {
    abstract fun instanceDao(): InstanceDao
}