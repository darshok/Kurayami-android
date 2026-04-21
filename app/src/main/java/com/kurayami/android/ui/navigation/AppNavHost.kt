package com.kurayami.android.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.kurayami.android.ui.screen.login.LoginLayout
import com.kurayami.android.ui.screen.main.LogoutButtonLayout
import com.kurayami.android.ui.screen.main.MainViewModel
import com.kurayami.android.ui.screen.main.TopAnimeChart

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle(false)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topAnimeList = viewModel.topChartFlow.collectAsLazyPagingItems()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.TopCharts,
            modifier = modifier
        ) {
            composable<AppRoutes.MyList> {
                if (isUserLoggedIn) {
                    LogoutButtonLayout(
                        onClickLogout = { viewModel.logout() })
                } else {
                    LoginLayout(modifier = Modifier)
                }
            }
            composable<AppRoutes.TopCharts> {
                TopAnimeChart(
                    uiState = uiState,
                    topAnimeList = topAnimeList
                )
            }
        }
    }
}
