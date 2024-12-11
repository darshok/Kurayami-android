package com.kurayami.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kurayami.data.source.api.MediaPagingSource
import com.kurayami.data.repository.MediaRepository
import com.kurayami.data.source.api.MediaApi
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val api: MediaApi) : MediaRepository {
    override fun getTopCharts(perPage: Int, type: MediaType, sort: List<MediaSort>) =
        Pager(config = PagingConfig(pageSize = perPage, prefetchDistance = 3), pagingSourceFactory = {
            MediaPagingSource(api, perPage, type, sort)
        }).flow
}