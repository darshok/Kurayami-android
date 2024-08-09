package com.kurayami.data.repository.impl

import android.net.Uri
import com.kurayami.data.repository.LoginRepository
import com.kurayami.data.repository.PreferencesDataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : LoginRepository {

    override suspend fun manageLoginData(uri: Uri) {
        Uri.parse("http://example.com?${uri.fragment}").getQueryParameter("access_token")?.let {
            preferencesDataStoreRepository.setAccessToken(it)
        }
    }

    override suspend fun manageLogout() {
        preferencesDataStoreRepository.removeAccessToken()
    }
}