package com.kurayami.android.ui.screen.login

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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

@Composable
fun LoginLayout(modifier: Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Button(onClick = { context.startActionView("https://anilist.co/api/v2/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&response_type=token") }) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

private fun Context.startActionView(uri: String) {
    try {
        with(Intent(Intent.ACTION_VIEW, Uri.parse(uri))) {
            startActivity(this)
        }
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.no_app_found_for_this_action), Toast.LENGTH_SHORT).show()
    }
}
