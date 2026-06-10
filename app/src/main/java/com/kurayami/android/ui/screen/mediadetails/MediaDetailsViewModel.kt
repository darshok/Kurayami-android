package com.kurayami.android.ui.screen.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurayami.android.ui.common.UiState
import com.kurayami.data.MediaDetailsQuery
import com.kurayami.data.repository.MediaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MediaDetailsViewModel.Factory::class)
class MediaDetailsViewModel @AssistedInject constructor(
    private val repository: MediaRepository,
    @Assisted private val id: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MediaDetailsQuery.Data>>(UiState.Loading)
    val uiState: StateFlow<UiState<MediaDetailsQuery.Data>> = _uiState.asStateFlow()

    init {
        getMediaDetails(id)
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

    @AssistedFactory
    interface Factory {
        fun create(id: Int): MediaDetailsViewModel
    }
}
