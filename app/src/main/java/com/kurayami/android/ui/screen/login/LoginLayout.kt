package com.kurayami.android.ui.screen.login

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kurayami.android.BuildConfig
import com.kurayami.android.R
import com.kurayami.common.ANILIST_AUTH_URL
import androidx.core.net.toUri

@Composable
fun LoginLayout(modifier: Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Button(onClick = {
            val authUrl = "$ANILIST_AUTH_URL?client_id=${BuildConfig.CLIENT_ID}&response_type=token"
            context.launchCustomTab(authUrl)
        }) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

private fun Context.launchCustomTab(url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .build()
    customTabsIntent.launchUrl(this, url.toUri())
}
