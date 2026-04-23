package com.kurayami.android.ui.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.type.MediaFormat

@Composable
fun AnimeCard(anime: MediaTopChartQuery.Medium, index: Int, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        CardContent(Modifier.padding(8.dp), anime, index)
    }
}

@Composable
fun CardContent(modifier: Modifier, anime: MediaTopChartQuery.Medium, index: Int) {
    var isLoaded by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0f,
        label = "loadingTransition",
        animationSpec = tween(durationMillis = 300)
    )

    Box(modifier = Modifier.graphicsLayer { this.alpha = alpha }) {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(bottomStart = 16.dp, topEnd = 8.dp))
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(
                modifier = modifier,
                text = index.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
        Row(
            modifier = modifier.padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = modifier
                    .height(140.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                model = anime.coverImage?.large,
                contentDescription = "anime thumbnail",
                onSuccess = { isLoaded = true },
            )
            Column(modifier = modifier.fillMaxWidth()) {
                Text(
                    modifier = modifier,
                    text = anime.title?.userPreferred ?: "No title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Row(modifier) {
                    val subtitleFontSize = 16.sp
                    Text(text = anime.seasonYear?.toString() ?: "---", fontSize = subtitleFontSize)
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "-",
                        fontSize = subtitleFontSize
                    )
                    Text(
                        text = anime.averageScore?.toString() ?: "---",
                        fontSize = subtitleFontSize
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AnimeCardPreview() {
    AnimeCard(
        anime = MediaTopChartQuery.Medium(
            __typename = "",
            id = 12,
            title = MediaTopChartQuery.Title(userPreferred = "Title"),
            seasonYear = 2020,
            meanScore = 98,
            averageScore = 98,
            studios = MediaTopChartQuery.Studios(listOf(MediaTopChartQuery.Node(name = "Studio"))),
            format = MediaFormat.TV,
            coverImage = MediaTopChartQuery.CoverImage(large = "")
        ),
        index = 1
    )
}
