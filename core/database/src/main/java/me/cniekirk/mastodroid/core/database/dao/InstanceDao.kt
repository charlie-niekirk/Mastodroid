package me.cniekirk.mastodroid.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.cniekirk.mastodroid.core.database.model.InstanceEntity

@Dao
interface InstanceDao {

    @Query("SELECT * FROM instanceEntity")
    fun getAll(): List<InstanceEntity>

    @Query("SELECT * FROM instanceEntity WHERE name LIKE '%' || :query || '%'")
    fun findByName(query: String): List<InstanceEntity>

    @Insert
    fun insertAll(vararg instances: InstanceEntity)

    @Delete
    fun delete(instance: InstanceEntity)
}