package com.kurayami.android.ui.screen.mediadetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kurayami.android.ui.common.UiState
import com.kurayami.android.ui.screen.topcharts.ErrorMessage

@Composable
fun MediaDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MediaDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UiState.Error -> {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Center),
                    message = state.message ?: "An error occurred",
                    onClickRetry = { /* Handle retry if needed, maybe using a saved id */ }
                )
            }

            is UiState.Success -> {
                val media = state.data.Media
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = media?.title?.userPreferred ?: "Unknown Title",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = media?.description ?: "No description available",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
