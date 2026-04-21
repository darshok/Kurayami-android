package com.kurayami.android.ui.screen.mylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurayami.android.R
import com.kurayami.android.ui.screen.login.LoginLayout
import com.kurayami.android.ui.screen.main.MainViewModel

@Composable
fun MyListScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle(false)

    if (isUserLoggedIn) {
        LogoutButtonLayout(
            modifier = modifier,
            onClickLogout = { viewModel.logout() })
    } else {
        LoginLayout(modifier = modifier)
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
