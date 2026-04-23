package com.kurayami.android.ui.screen.mediadetails

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.kurayami.android.ui.common.UiState
import com.kurayami.android.ui.screen.topcharts.ErrorMessage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaDetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
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
                    onClickRetry = { /* Handle retry logic if implemented */ }
                )
            }

            is UiState.Success -> {
                val media = state.data.Media
                if (media != null) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = media.bannerImage ?: media.coverImage?.extraLarge,
                                    contentDescription = "Banner Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                )
                                // Dark overlay to make text more legible
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                        .background(Color.Black.copy(alpha = 0.5f))
                                )

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    with(sharedTransitionScope) {
                                        AsyncImage(
                                            model = media.coverImage?.large,
                                            contentDescription = "Cover Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(150.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .sharedElement(
                                                    sharedContentState = rememberSharedContentState(
                                                        key = "cover-${media.id}"
                                                    ),
                                                    animatedVisibilityScope = animatedVisibilityScope
                                                )
                                        )
                                    }

                                    Column(modifier = Modifier.padding(bottom = 8.dp)) {
                                        val title = media.title
                                        Text(
                                            text = title?.userPreferred ?: "Unknown Title",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )

                                        val englishTitle = title?.english
                                        val userPreferredTitle = title?.userPreferred
                                        if (englishTitle != null && englishTitle != userPreferredTitle) {
                                            Text(
                                                text = englishTitle,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val genres = media.genres
                                if (!genres.isNullOrEmpty()) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    ) {
                                        items(genres.filterNotNull()) { genre ->
                                            SuggestionChip(
                                                onClick = { },
                                                label = { Text(genre) }
                                            )
                                        }
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        InfoItem(
                                            "Score",
                                            media.averageScore?.let { "$it%" } ?: "N/A")
                                        InfoItem("Format", media.format?.name ?: "N/A")
                                        InfoItem("Episodes", media.episodes?.toString() ?: "N/A")
                                        InfoItem("Status", media.status?.name ?: "N/A")
                                    }
                                }

                                Text(
                                    text = "Description",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = media.description ?: "No description available.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                } else {
                    Text("No media data", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
