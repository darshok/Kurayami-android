package com.kurayami.android.ui.screen.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kurayami.data.repository.LoginRepository
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.repository.PreferencesDataStoreRepository
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MediaTopChartUiState(
    val isLoading: Boolean = true,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    preferencesDataStoreRepository: PreferencesDataStoreRepository,
    mediaRepository: MediaRepository,
    private val loginRepository: LoginRepository,
) : ViewModel(), MainActions {

    val isUserLoggedIn = preferencesDataStoreRepository.getAccessToken().map { it != null }

    private val _uiState by lazy { MutableStateFlow(MediaTopChartUiState()) }
    val uiState: StateFlow<MediaTopChartUiState> by lazy { _uiState.asStateFlow() }

    val topChartFlow by lazy {
        mediaRepository.getTopCharts(
            25,
            MediaType.ANIME,
            listOf(MediaSort.SCORE_DESC)
        ).onCompletion { _uiState.update { it.copy(isLoading = false) } }.cachedIn(viewModelScope)
    }

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