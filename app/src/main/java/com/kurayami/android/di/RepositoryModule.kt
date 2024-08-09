package com.kurayami.android.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kurayami.android.data.repository.LoginRepository
import com.kurayami.android.data.repository.PreferencesDataStoreRepository
import com.kurayami.android.data.repository.impl.LoginRepositoryImpl
import com.kurayami.android.data.repository.impl.PreferencesDataStoreRepositoryImpl
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
    fun providePreferencesDataStoreRepository(dataStore: DataStore<Preferences>): PreferencesDataStoreRepository = PreferencesDataStoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideLoginRepository(preferencesDataStoreRepository: PreferencesDataStoreRepositoryImpl): LoginRepository = LoginRepositoryImpl(preferencesDataStoreRepository)
}