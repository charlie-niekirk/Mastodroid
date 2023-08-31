package me.cniekirk.mastodroid.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InstanceDao {

    @Query("SELECT * FROM instanceEntity")
    fun getAll(): List<InstanceEntity>

    @Insert
    fun insertAll(vararg instances: InstanceEntity)

    @Delete
    fun delete(instance: InstanceEntity)
}