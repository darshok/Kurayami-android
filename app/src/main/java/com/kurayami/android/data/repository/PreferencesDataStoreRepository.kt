package com.kurayami.android.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {

    fun getAccessToken(): Flow<String?>
    suspend fun setAccessToken(value: String?)
    suspend fun removeAccessToken()
}