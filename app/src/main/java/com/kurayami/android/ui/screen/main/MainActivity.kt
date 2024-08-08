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
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kurayami.android.R
import com.kurayami.android.ui.screen.login.LoginLayout
import com.kurayami.android.ui.theme.KurayamiTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel.manageIntentData(intent.data)

        setContent {
            KurayamiTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        getString(
                            R.string.app_name
                        )
                    })
                }) { innerPadding ->
                    LoginLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
