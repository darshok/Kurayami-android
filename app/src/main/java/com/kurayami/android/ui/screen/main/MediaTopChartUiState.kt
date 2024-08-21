package com.kurayami.android.ui.screen.main

import androidx.paging.PagingData
import com.kurayami.data.MediaTopChartQuery
import kotlinx.coroutines.flow.Flow

data class MediaTopChartUiState(
    val media: Flow<PagingData<MediaTopChartQuery.Medium>>,
    val isLoading: Boolean = false,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1
)
