package me.cniekirk.mastodroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.domain.usecase.GetServersUseCase
import me.cniekirk.mastodroid.domain.usecase.GetServersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindings {

    @Binds
    abstract fun bindGetServersUseCase(getServersUseCaseImpl: GetServersUseCaseImpl): GetServersUseCase
}