package com.kurayami.android.ui.screen.main.topcharts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopChartsViewModel @Inject constructor(
    mediaRepository: MediaRepository
) : ViewModel() {

    val topCharts: Flow<PagingData<MediaTopChartQuery.Medium>> = mediaRepository.getTopCharts(
        25,
        MediaType.ANIME,
        listOf(MediaSort.SCORE_DESC)
    ).cachedIn(viewModelScope)
}
