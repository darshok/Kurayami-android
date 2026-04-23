package com.kurayami.data.source.api

import com.apollographql.apollo.ApolloCall
import com.kurayami.data.MediaDetailsQuery
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType


interface MediaApi {
    fun getTopCharts(
        page: Int,
        perPage: Int,
        type: MediaType,
        sort: List<MediaSort>,
    ): ApolloCall<MediaTopChartQuery.Data>

    fun getMediaDetails(
        id: Int
    ): ApolloCall<MediaDetailsQuery.Data>
}
