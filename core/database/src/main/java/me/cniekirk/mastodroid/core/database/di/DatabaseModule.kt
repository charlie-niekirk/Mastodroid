package me.cniekirk.mastodroid.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.database.InstanceDatabase
import me.cniekirk.mastodroid.core.database.ServerConfigurationDatabase
import me.cniekirk.mastodroid.core.database.dao.InstanceDao
import me.cniekirk.mastodroid.core.database.dao.ServerConfigurationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideInstanceDatabase(@ApplicationContext context: Context): InstanceDatabase {
        return Room.databaseBuilder(
            context,
            InstanceDatabase::class.java,
            "instance-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideServerConfigurationDatabase(@ApplicationContext context: Context): ServerConfigurationDatabase {
        return Room.databaseBuilder(
            context,
            ServerConfigurationDatabase::class.java,
            "server-configuration-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideInstanceDao(instanceDatabase: InstanceDatabase): InstanceDao =
        instanceDatabase.instanceDao()

    @Provides
    @Singleton
    fun provideServerConfigurationDao(serverConfigurationDatabase: ServerConfigurationDatabase): ServerConfigurationDao =
        serverConfigurationDatabase.serverConfigurationDao()
}