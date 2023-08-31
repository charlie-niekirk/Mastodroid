package me.cniekirk.mastodroid.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InstanceEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "users") val users: String?
)
