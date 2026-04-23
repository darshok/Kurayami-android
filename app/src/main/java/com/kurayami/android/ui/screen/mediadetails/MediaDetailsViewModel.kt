package com.kurayami.android.ui.screen.mediadetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurayami.android.ui.common.UiState
import com.kurayami.data.MediaDetailsQuery
import com.kurayami.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel @Inject constructor(
    private val repository: MediaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MediaDetailsQuery.Data>>(UiState.Loading)
    val uiState: StateFlow<UiState<MediaDetailsQuery.Data>> = _uiState.asStateFlow()

    init {
        val id: Int? = savedStateHandle["id"]
        id?.let {
            getMediaDetails(it)
        } ?: {
            _uiState.value = UiState.Error(message = "Media ID not found")
        }
    }

    fun getMediaDetails(id: Int) {
        viewModelScope.launch {
            repository.getMediaDetails(id)
                .catch { e ->
                    _uiState.value = UiState.Error(message = e.localizedMessage, throwable = e)
                }
                .collect { data ->
                    _uiState.value = data?.let {
                        UiState.Success(it)
                    } ?: UiState.Error(message = "No data found")
                }
        }
    }
}
