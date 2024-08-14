package com.kurayami.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.kurayami.common.ANILIST_GRAPHQL_URL
import com.kurayami.data.repository.PreferencesDataStoreRepository
import com.kurayami.data.source.interceptor.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApolloClient(authorizationInterceptor: AuthorizationInterceptor): ApolloClient {
        val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

        return ApolloClient.Builder()
            .serverUrl(ANILIST_GRAPHQL_URL)
            .addHttpInterceptor(authorizationInterceptor)
            .normalizedCache(cacheFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(preferencesDataStoreRepository: PreferencesDataStoreRepository): AuthorizationInterceptor {
        return AuthorizationInterceptor(preferencesDataStoreRepository)
    }

}