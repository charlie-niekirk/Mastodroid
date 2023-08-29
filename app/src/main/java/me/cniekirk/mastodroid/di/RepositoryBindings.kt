package me.cniekirk.mastodroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.data.repository.AuthenticationRepositoryImpl
import me.cniekirk.mastodroid.data.repository.InstancesRepositoryImpl
import me.cniekirk.mastodroid.domain.repository.AuthenticationRepository
import me.cniekirk.mastodroid.domain.repository.InstancesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindings {

    @Binds
    abstract fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindInstancesRepository(instancesRepositoryImpl: InstancesRepositoryImpl): InstancesRepository
}