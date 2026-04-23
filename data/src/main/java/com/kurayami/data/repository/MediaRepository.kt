package com.kurayami.data.repository

import androidx.paging.PagingData
import com.kurayami.data.MediaDetailsQuery
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getTopCharts(
        perPage: Int,
        type: MediaType,
        sort: List<MediaSort>
    ): Flow<PagingData<MediaTopChartQuery.Medium>>

    fun getMediaDetails(id: Int): Flow<MediaDetailsQuery.Data?>
}
