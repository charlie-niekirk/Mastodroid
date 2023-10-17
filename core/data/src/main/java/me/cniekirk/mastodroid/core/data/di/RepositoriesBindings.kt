package me.cniekirk.mastodroid.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.InstancesRepository
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.data.repository.UserAuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.UserInstancesRepository
import me.cniekirk.mastodroid.core.data.repository.UserStatusRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesBindings {

    @Binds
    abstract fun bindUserInstancesRepository(userInstancesRepository: UserInstancesRepository): InstancesRepository

    @Binds
    abstract fun bindUserAuthenticationRepository(userAuthenticationRepository: UserAuthenticationRepository): AuthenticationRepository

    @Binds
    abstract fun bindUserStatusRepository(userStatusRepository: UserStatusRepository): StatusRepository
}