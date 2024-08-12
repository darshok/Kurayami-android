package com.kurayami.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
    fun providePreferencesDataStoreRepository(dataStore: DataStore<Preferences>): com.kurayami.data.repository.PreferencesDataStoreRepository =
        com.kurayami.data.repository.impl.PreferencesDataStoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideLoginRepository(preferencesDataStoreRepository: com.kurayami.data.repository.impl.PreferencesDataStoreRepositoryImpl): com.kurayami.data.repository.LoginRepository =
        com.kurayami.data.repository.impl.LoginRepositoryImpl(preferencesDataStoreRepository)
}