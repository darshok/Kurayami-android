package com.kurayami.android.ui.screen.topcharts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MediaTopChartUiState(
    val isLoading: Boolean = true,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

@HiltViewModel
class TopChartsViewModel @Inject constructor(
    mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MediaTopChartUiState())
    val uiState: StateFlow<MediaTopChartUiState> = _uiState.asStateFlow()

    val topChartFlow = mediaRepository.getTopCharts(
        25,
        MediaType.ANIME,
        listOf(MediaSort.SCORE_DESC)
    ).cachedIn(viewModelScope)
}
