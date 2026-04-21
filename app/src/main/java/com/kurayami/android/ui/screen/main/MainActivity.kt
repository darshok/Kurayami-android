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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.kurayami.android.R
import com.kurayami.android.ui.components.AnimeCard
import com.kurayami.android.ui.components.BottomNavigation
import com.kurayami.android.ui.navigation.AppNavHost
import com.kurayami.android.ui.navigation.AppRoutes
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
            val navController = rememberNavController()
            KurayamiTheme {
                MainScaffold(navController, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val titleRes = when {
        currentDestination?.hierarchy?.any { it.hasRoute<AppRoutes.MyList>() } == true -> R.string.my_list_title
        else -> R.string.top_anime_title
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(stringResource(titleRes))
            })
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
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
