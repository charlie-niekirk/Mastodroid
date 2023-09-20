package me.cniekirk.mastodroid.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.network.MastodroidNetworkDataSource
import me.cniekirk.mastodroid.core.network.service.RetrofitMastodroidNetwork

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindings {

    @Binds
    abstract fun bindNetworkDataSource(retrofitMastodroidNetwork: RetrofitMastodroidNetwork): MastodroidNetworkDataSource
}