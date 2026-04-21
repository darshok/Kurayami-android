package com.kurayami.android.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kurayami.android.ui.screen.main.MainViewModel
import com.kurayami.android.ui.screen.main.mylist.MyListScreen
import com.kurayami.android.ui.screen.main.topcharts.TopChartsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.TopCharts,
            modifier = modifier
        ) {
            composable<AppRoutes.MyList> {
                MyListScreen(viewModel = viewModel)
            }
            composable<AppRoutes.TopCharts> {
                TopChartsScreen()
            }
        }
    }
}
