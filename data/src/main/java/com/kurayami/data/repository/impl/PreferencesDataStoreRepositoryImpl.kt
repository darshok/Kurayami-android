package com.kurayami.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kurayami.common.getValue
import com.kurayami.common.setValue
import com.kurayami.data.repository.PreferencesDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataStoreRepository {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    }

    override fun getAccessToken(): Flow<String?> = dataStore.getValue(ACCESS_TOKEN_KEY)

    override suspend fun setAccessToken(value: String?) {
        dataStore.setValue(ACCESS_TOKEN_KEY, value)
    }

    override suspend fun removeAccessToken() {
        dataStore.edit { it.remove(ACCESS_TOKEN_KEY) }
    }
}