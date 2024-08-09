package com.kurayami.android.ui.screen.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurayami.data.repository.LoginRepository
import com.kurayami.data.repository.PreferencesDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginRepository: LoginRepository, private val preferencesDataStoreRepository: PreferencesDataStoreRepository) :
    ViewModel(), MainActions {

    val isUserLoggedIn = preferencesDataStoreRepository.getAccessToken().map { it != null }

    override fun manageIntentData(data: Uri?) {
        data?.let { dataUri ->
            viewModelScope.launch {
                if (isAuthenticationUri(dataUri)) {
                    loginRepository.manageLoginData(dataUri)
                }
            }
        }
    }

    // TODO: provisional way to test logout
    fun logout() {
        viewModelScope.launch {
            loginRepository.manageLogout()
        }
    }

    private fun isAuthenticationUri(data: Uri?) =
        data?.scheme == "kurayami"
}