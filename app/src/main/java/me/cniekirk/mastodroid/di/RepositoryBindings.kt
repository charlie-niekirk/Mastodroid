package me.cniekirk.mastodroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.data.repository.AuthenticationRepositoryImpl
import me.cniekirk.mastodroid.domain.repository.AuthenticationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindings {

    @Binds
    abstract fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}