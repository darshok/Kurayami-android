package com.kurayami.android.ui.screen.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurayami.data.repository.LoginRepository
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.repository.PreferencesDataStoreRepository
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository,
    private val mediaRepository: MediaRepository,
) :
    ViewModel(), MainActions {

    val isUserLoggedIn = preferencesDataStoreRepository.getAccessToken().map { it != null }

    private val _uiState by lazy { getUiState() }
    val uiState: StateFlow<MediaTopChartUiState> by lazy { _uiState.asStateFlow() }

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

    private fun getUiState() =
        MutableStateFlow(MediaTopChartUiState(getAnimeChart()))

    private fun getAnimeChart() = mediaRepository.getTopCharts(
        10,
        MediaType.ANIME,
        listOf(MediaSort.SCORE_DESC)
    )

    private fun isAuthenticationUri(data: Uri?) =
        data?.scheme == "kurayami"
}