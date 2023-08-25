package me.cniekirk.mastodroid.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.cniekirk.mastodroid.data.remote.HostSelectionInterceptor
import me.cniekirk.mastodroid.data.remote.MastodonService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
    fun provideRetrofit(okHttpClient: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mastodon.social")
            .callFactory { okHttpClient.get().newCall(it) }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMastodonService(retrofit: Retrofit): MastodonService =
        retrofit.create(MastodonService::class.java)
}