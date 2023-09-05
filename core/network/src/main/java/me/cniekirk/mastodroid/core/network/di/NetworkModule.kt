package me.cniekirk.mastodroid.core.network.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.core.network.service.InstancesService
import me.cniekirk.mastodroid.core.network.service.MastodonService
import me.cniekirk.mastodroid.core.network.util.HostSelectionInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHostSelectionInterceptor(): HostSelectionInterceptor = HostSelectionInterceptor()

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, 102400L)
    }

    @Provides
    @Singleton
    fun provideOkHttp(hostSelectionInterceptor: HostSelectionInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(hostSelectionInterceptor)
            .cache(cache)
            .callTimeout(Duration.ofMinutes(1))
            .connectTimeout(Duration.ofMinutes(1))
            .readTimeout(Duration.ofMinutes(1))
            .writeTimeout(Duration.ofMinutes(1))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    @Named("mastodon")
    fun provideRetrofit(okHttpClient: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mastodon.social")
            .callFactory { okHttpClient.get().newCall(it) }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @Named("instances")
    fun provideInstancesRetrofit(okHttpClient: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://instances.social")
            .callFactory { okHttpClient.get().newCall(it) }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMastodonService(@Named("mastodon") retrofit: Retrofit): MastodonService =
        retrofit.create(MastodonService::class.java)

    @Provides
    @Singleton
    fun provideInstancesService(@Named("instances") retrofit: Retrofit): InstancesService =
        retrofit.create(InstancesService::class.java)
}