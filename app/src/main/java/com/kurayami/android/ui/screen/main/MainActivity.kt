package com.kurayami.android.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurayami.android.R
import com.kurayami.android.ui.screen.login.LoginLayout
import com.kurayami.android.ui.theme.KurayamiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel.manageIntentData(intent.data)

        val userLoggedIn = runBlocking {
            viewModel.isUserLoggedIn.first()
        }

        setContent {
            val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsStateWithLifecycle(userLoggedIn)
            KurayamiTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        getString(
                            R.string.app_name
                        )
                    })
                }) { innerPadding ->
                    // TODO: provisional way to test login/logout
                    if (isUserLoggedIn) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Button(onClick = { viewModel.logout() }) {
                                Text(text = stringResource(id = R.string.logout))
                            }
                        }
                    } else {
                        LoginLayout(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
