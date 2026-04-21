package com.kurayami.android.ui.screen.topcharts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurayami.android.ui.components.AnimeCard
import com.kurayami.data.MediaTopChartQuery

@Composable
fun TopChartsScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: TopChartsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topAnimeList = viewModel.topChartFlow.collectAsLazyPagingItems()

    TopAnimeChart(
        modifier = modifier,
        topAnimeList = topAnimeList
    )
}

@Composable
fun TopAnimeChart(
    modifier: Modifier = Modifier,
    topAnimeList: LazyPagingItems<MediaTopChartQuery.Medium>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = topAnimeList.itemCount,
            key = { index -> topAnimeList.peek(index)?.id ?: index },
            contentType = { "Anime" }
        ) { index ->
            topAnimeList[index]?.let { anime ->
                AnimeCard(anime, index + 1)
            }
        }
    }
}
