package com.kurayami.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.source.api.MediaApi
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import java.io.IOException
import javax.inject.Inject

class MediaPagingSource @Inject constructor(
    private val api: MediaApi,
    private val perPage: Int,
    private val mediaType: MediaType,
    private val sort: List<MediaSort>,
) :
    PagingSource<Int, MediaTopChartQuery.Medium>() {
    override fun getRefreshKey(state: PagingState<Int, MediaTopChartQuery.Medium>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaTopChartQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val mediaPage = api.getTopCharts(page, perPage, mediaType, sort).execute().data?.Page
            val media = mediaPage?.media?.filterNotNull() ?: listOf()
            LoadResult.Page(
                data = media,
                prevKey = getPrevKey(page),
                nextKey = getNextKey(mediaPage, page)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    private fun getPrevKey(page: Int) = if (page > 1) page - 1 else null

    private fun getNextKey(mediaPage: MediaTopChartQuery.Page?, page: Int) =
        if (mediaPage?.pageInfo?.hasNextPage == true) page + 1 else null
}