package com.kurayami.android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kurayami.android.R
import com.kurayami.android.ui.navigation.AppRoutes

@Composable
fun BottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        val myListSelected = currentDestination?.hierarchy?.any { it.hasRoute<AppRoutes.MyList>() } == true
        NavigationBarItem(
            icon = { Icon(if (myListSelected) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "my list icon") },
            label = { Text(stringResource(R.string.my_list_title)) },
            selected = myListSelected,
            onClick = {
                navController.navigate(AppRoutes.MyList) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        val topChartsSelected = currentDestination?.hierarchy?.any { it.hasRoute<AppRoutes.TopCharts>() } == true
        NavigationBarItem(
            icon = { Icon(painterResource(if (topChartsSelected) R.drawable.ic_leaderboard_filled else R.drawable.ic_leaderboard_outlined), contentDescription = "top charts icon") },
            label = { Text(stringResource(R.string.top_charts_title)) },
            selected = topChartsSelected,
            onClick = {
                navController.navigate(AppRoutes.TopCharts) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
