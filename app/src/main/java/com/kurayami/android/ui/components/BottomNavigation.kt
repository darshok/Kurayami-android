package com.kurayami.android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kurayami.android.R
import com.kurayami.android.ui.navigation.AppRoutes
import com.kurayami.android.ui.navigation.NavigationState
import com.kurayami.android.ui.navigation.Navigator

@Composable
fun BottomNavigation(navigationState: NavigationState, navigator: Navigator) {
    NavigationBar {
        val myListSelected = navigationState.topLevelRoute == AppRoutes.MyList
        NavigationBarItem(
            icon = { Icon(if (myListSelected) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "my list icon") },
            label = { Text(stringResource(R.string.my_list_title)) },
            selected = myListSelected,
            onClick = {
                navigator.navigate(AppRoutes.MyList)
            }
        )
        val topChartsSelected = navigationState.topLevelRoute == AppRoutes.TopCharts
        NavigationBarItem(
            icon = { Icon(painterResource(if (topChartsSelected) R.drawable.ic_leaderboard_filled else R.drawable.ic_leaderboard_outlined), contentDescription = "top charts icon") },
            label = { Text(stringResource(R.string.top_charts_title)) },
            selected = topChartsSelected,
            onClick = {
                navigator.navigate(AppRoutes.TopCharts)
            }
        )
    }
}
