package com.kurayami.android.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kurayami.android.R
import com.kurayami.android.ui.components.BottomNavigation
import com.kurayami.android.ui.navigation.AppNavHost
import com.kurayami.android.ui.navigation.AppRoutes
import com.kurayami.android.ui.navigation.Navigator
import com.kurayami.android.ui.navigation.rememberNavigationState
import com.kurayami.android.ui.theme.KurayamiTheme
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
            val navigationState = rememberNavigationState(
                startRoute = AppRoutes.TopCharts,
                topLevelRoutes = setOf(AppRoutes.TopCharts, AppRoutes.MyList)
            )
            val navigator = remember { Navigator(navigationState) }
            
            KurayamiTheme {
                MainScaffold(navigationState, navigator, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navigationState: com.kurayami.android.ui.navigation.NavigationState,
    navigator: Navigator,
    viewModel: MainViewModel
) {
    val currentDestination = navigationState.backStacks[navigationState.topLevelRoute]?.last()
    
    val titleRes = when (currentDestination) {
        is AppRoutes.MyList -> R.string.my_list_title
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
            BottomNavigation(navigationState, navigator)
        }
    ) { innerPadding ->
        AppNavHost(
            navigationState = navigationState,
            navigator = navigator,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}
