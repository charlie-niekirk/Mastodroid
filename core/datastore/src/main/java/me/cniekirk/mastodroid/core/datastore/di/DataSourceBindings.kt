package me.cniekirk.mastodroid.core.datastore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.datastore.DataStorePreferencesDataSource
import me.cniekirk.mastodroid.core.datastore.MastodroidPreferencesDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBindings {

    @Binds
    abstract fun bindMastodroidPreferencesDataSource(dataStorePreferencesDataSource: DataStorePreferencesDataSource):
            MastodroidPreferencesDataSource
}