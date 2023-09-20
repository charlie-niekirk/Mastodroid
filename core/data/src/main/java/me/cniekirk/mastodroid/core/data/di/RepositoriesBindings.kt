package me.cniekirk.mastodroid.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.data.repository.InstancesRepository
import me.cniekirk.mastodroid.core.data.repository.UserInstancesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesBindings {

    @Binds
    abstract fun bindUserInstancesRepository(userInstancesRepository: UserInstancesRepository): InstancesRepository
}