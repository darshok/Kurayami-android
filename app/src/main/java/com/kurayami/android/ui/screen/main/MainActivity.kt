package com.kurayami.android.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurayami.android.R
import com.kurayami.android.ui.theme.KurayamiTheme
import com.kurayami.data.MediaTopChartQuery
import com.kurayami.data.type.MediaFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel.manageIntentData(intent.data)

        setContent {
            KurayamiTheme {
                BaseScaffold()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(viewModel: MainViewModel = hiltViewModel()) {
    val userLoggedIn = runBlocking {
        viewModel.isUserLoggedIn.first()
    }

    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle(userLoggedIn)

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            stringResource(
                R.string.app_name
            )
        })
    }) { innerPadding ->
        // TODO: provisional way to test pagination
        TopAnimeChart(modifier = Modifier.padding(innerPadding))

        // TODO: provisional way to test login/logout
//        if (isUserLoggedIn) {
//            LogoutButtonLayout(
//                modifier = Modifier.padding(innerPadding),
//                onClickLogout = { viewModel.logout() })
//        } else {
//            LoginLayout(modifier = Modifier.padding(innerPadding))
//        }
    }
}

@Composable
fun TopAnimeChart(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topAnimeList = uiState.media.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(topAnimeList.itemCount) { index ->
            topAnimeList[index]?.let { anime ->
                AnimeCard(anime, index + 1)
            }
        }
    }
}

@Composable
fun AnimeCard(anime: MediaTopChartQuery.Medium, index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        CardContent(Modifier.padding(8.dp), anime, index)
    }
}

@Composable
fun CardContent(modifier: Modifier, anime: MediaTopChartQuery.Medium, index: Int) {
    Box {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(bottomStart = 16.dp, topEnd = 8.dp)).background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(
                modifier = modifier,
                text = index.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                modifier = modifier,
                text = anime.title?.userPreferred ?: "No title",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Row(modifier) {
                Text(text = anime.seasonYear?.toString() ?: "---", fontSize = 16.sp)
                Text(modifier = Modifier.padding(horizontal = 8.dp), text = "-", fontSize = 16.sp)
                Text(text = anime.averageScore?.toString() ?: "---", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun LogoutButtonLayout(modifier: Modifier = Modifier, onClickLogout: () -> Unit = {}) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(onClick = onClickLogout) {
            Text(text = stringResource(id = R.string.logout))
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
        ),
        index = 1
    )
}