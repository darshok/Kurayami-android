package com.kurayami.android.ui.screen.topcharts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kurayami.android.R
import com.kurayami.android.ui.common.UiState
import com.kurayami.android.ui.components.AnimeCard
import com.kurayami.data.MediaTopChartQuery

@Composable
fun TopChartsScreen(
    modifier: Modifier = Modifier,
    viewModel: TopChartsViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit = {}
) {
    val topAnimeList = viewModel.topCharts.collectAsLazyPagingItems()

    val uiState by remember(topAnimeList.loadState.refresh) {
        derivedStateOf {
            when (val refreshState = topAnimeList.loadState.refresh) {
                is LoadState.Loading -> UiState.Loading
                is LoadState.Error -> UiState.Error(
                    message = refreshState.error.localizedMessage,
                    throwable = refreshState.error
                )

                else -> UiState.Success(Unit)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UiState.Error -> {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Center),
                    message = (uiState as UiState.Error).message
                        ?: stringResource(R.string.generic_error),
                    onClickRetry = { topAnimeList.refresh() }
                )
            }

            is UiState.Success -> {
                TopAnimeChart(
                    modifier = Modifier.fillMaxSize(),
                    topAnimeList = topAnimeList,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun TopAnimeChart(
    modifier: Modifier = Modifier,
    topAnimeList: LazyPagingItems<MediaTopChartQuery.Medium>,
    onItemClick: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = topAnimeList.itemCount,
            key = topAnimeList.itemKey { it.id },
            contentType = topAnimeList.itemContentType { "Anime" }
        ) { index ->
            topAnimeList[index]?.let { anime ->
                AnimeCard(anime, index + 1, onClick = { onItemClick(anime.id) })
            }
        }

        when (val appendState = topAnimeList.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorMessage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        message = appendState.error.localizedMessage
                            ?: stringResource(R.string.generic_error),
                        onClickRetry = { topAnimeList.retry() }
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primaryContainer
        )
        Button(onClick = onClickRetry) {
            Text(text = stringResource(R.string.generic_retry))
        }
    }
}
