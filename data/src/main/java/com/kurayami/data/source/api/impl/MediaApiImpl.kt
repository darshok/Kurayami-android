package com.kurayami.data.source.api.impl

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.source.api.MediaApi
import com.kurayami.data.type.MediaSort
import com.kurayami.data.type.MediaType
import javax.inject.Inject

class MediaApiImpl @Inject constructor(private val client: ApolloClient) : MediaApi {
    override fun getTopCharts(page: Int, perPage: Int, type: MediaType, sort: List<MediaSort>) =
        client.query(
            MediaTopChartQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                type = Optional.present(type),
                sort = Optional.present(sort)
            )
        )
}