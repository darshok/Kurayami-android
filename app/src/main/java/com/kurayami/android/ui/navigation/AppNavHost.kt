package com.kurayami.android.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import com.kurayami.android.ui.screen.main.MainViewModel
import com.kurayami.android.ui.screen.mediadetails.MediaDetailsScreen
import com.kurayami.android.ui.screen.mediadetails.MediaDetailsViewModel
import com.kurayami.android.ui.screen.mylist.MyListScreen
import com.kurayami.android.ui.screen.topcharts.TopChartsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    SharedTransitionLayout {
        val entryProvider = entryProvider<NavKey> {
            entry<AppRoutes.MyList> {
                MyListScreen(viewModel = viewModel)
            }
            entry<AppRoutes.TopCharts> { key ->
                TopChartsScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    onItemClick = { id ->
                        navigator.navigate(AppRoutes.MediaDetails(id))
                    }
                )
            }
            entry<AppRoutes.MediaDetails> { key ->
                MediaDetailsScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    viewModel = hiltViewModel(
                        creationCallback = { factory: MediaDetailsViewModel.Factory ->
                            factory.create(key.id)
                        }
                    )
                )
            }
        }

        NavDisplay(
            modifier = modifier,
            entries = navigationState.toEntries(entryProvider),
            onBack = { navigator.goBack() },
            sharedTransitionScope = this@SharedTransitionLayout,
        )
    }
}
