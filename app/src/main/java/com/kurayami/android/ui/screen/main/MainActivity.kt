package com.kurayami.android.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurayami.android.R
import com.kurayami.android.ui.components.AnimeCard
import com.kurayami.android.ui.components.BottomNavigation
import com.kurayami.android.ui.theme.KurayamiTheme
import com.kurayami.data.MediaTopChartQuery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel.manageIntentData(intent.data)

        setContent {
            val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle(false)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val topAnimeList = viewModel.topChartFlow.collectAsLazyPagingItems()
            KurayamiTheme {
                MainScaffold(isUserLoggedIn, uiState, topAnimeList, viewModel::logout)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    isUserLoggedIn: Boolean,
    uiState: MediaTopChartUiState,
    topAnimeList: LazyPagingItems<MediaTopChartQuery.Medium>,
    logoutAction: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                stringResource(
                    R.string.top_anime_title
                )
            )
        })
    }, bottomBar = {
        BottomNavigation()
    }) { innerPadding ->
        // TODO: provisional way to test pagination
        TopAnimeChart(modifier = Modifier.padding(innerPadding), uiState, topAnimeList)

//        // TODO: provisional way to test login/logout
//        if (isUserLoggedIn) {
//            LogoutButtonLayout(
//                modifier = Modifier.padding(innerPadding),
//                onClickLogout = { logoutAction() })
//        } else {
//            LoginLayout(modifier = Modifier.padding(innerPadding))
//        }
    }
}

@Composable
fun TopAnimeChart(
    modifier: Modifier = Modifier,
    uiState: MediaTopChartUiState,
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