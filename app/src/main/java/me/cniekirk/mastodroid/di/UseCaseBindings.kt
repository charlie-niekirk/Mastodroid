package me.cniekirk.mastodroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.domain.usecase.SearchServersUseCase
import me.cniekirk.mastodroid.domain.usecase.SearchServersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindings {

    @Binds
    abstract fun bindSearchServersUseCase(searchServersUseCaseImpl: SearchServersUseCaseImpl): SearchServersUseCase
}