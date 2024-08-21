package com.kurayami.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kurayami.data.repository.LoginRepository
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.repository.PreferencesDataStoreRepository
import com.kurayami.data.repository.impl.LoginRepositoryImpl
import com.kurayami.data.repository.impl.MediaRepositoryImpl
import com.kurayami.data.repository.impl.PreferencesDataStoreRepositoryImpl
import com.kurayami.data.source.api.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePreferencesDataStoreRepository(dataStore: DataStore<Preferences>): PreferencesDataStoreRepository =
        PreferencesDataStoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideLoginRepository(preferencesDataStoreRepository: PreferencesDataStoreRepositoryImpl): LoginRepository =
        LoginRepositoryImpl(preferencesDataStoreRepository)

    @Provides
    @Singleton
    fun provideMediaRepository(api: MediaApi): MediaRepository =
        MediaRepositoryImpl(api)
}