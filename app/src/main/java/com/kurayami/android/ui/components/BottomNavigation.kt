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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kurayami.android.R

@Composable
fun BottomNavigation() {
    var selected by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(if (selected == 0) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "my list icon") },
            label = { Text("My list") },
            selected = selected == 0,
            onClick = { selected = 0 }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(if (selected == 1) R.drawable.ic_leaderboard_filled else R.drawable.ic_leaderboard_outlined), contentDescription = "top charts icon") },
            label = { Text("Top charts") },
            selected = selected == 1,
            onClick = { selected = 1 }
        )
    }
}


@Preview
@Composable
fun BottomNavigationPreview() {
    BottomNavigation()
}