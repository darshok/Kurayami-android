package com.kurayami.di

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.okHttpClient
import com.kurayami.common.ANILIST_GRAPHQL_URL
import com.kurayami.data.repository.PreferencesDataStoreRepository
import com.kurayami.data.source.api.MediaApi
import com.kurayami.data.source.api.impl.MediaApiImpl
import com.kurayami.data.source.interceptor.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApolloClient(
        authorizationInterceptor: AuthorizationInterceptor,
        httpClient: OkHttpClient,
    ): ApolloClient {
        val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

        return ApolloClient.Builder()
            .serverUrl(ANILIST_GRAPHQL_URL)
            .addHttpInterceptor(authorizationInterceptor)
            .okHttpClient(httpClient)
            .normalizedCache(cacheFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(preferencesDataStoreRepository: PreferencesDataStoreRepository): AuthorizationInterceptor {
        return AuthorizationInterceptor(preferencesDataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideMediaApi(apolloClient: ApolloClient): MediaApi {
        return MediaApiImpl(apolloClient)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            Log.v("API-CALL", "message: $it")
        }.setLevel(
            HttpLoggingInterceptor.Level.BODY,
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }
}