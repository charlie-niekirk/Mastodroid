package me.cniekirk.mastodroid.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.data.local.db.InstanceDao
import me.cniekirk.mastodroid.data.local.db.InstanceDatabase
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
    fun provideInstanceDao(instanceDatabase: InstanceDatabase): InstanceDao =
        instanceDatabase.instanceDao()
}