package com.badger.familyorgfe.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.badger.familyorgfe.features.appjourney.AppJourney
import com.badger.familyorgfe.features.authjourney.AuthJourney
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: IMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProcessCameraProvider.getInstance(this)
        setContent {
            FamilyOrganizerTheme {
                JourneyScreen()
            }
        }
    }

    @Composable
    private fun JourneyScreen() {
        val auth by viewModel.isAuth.collectAsState()
        val appModifier = Modifier.fillMaxSize()

        if (auth) {
            AppJourney(modifier = appModifier)
        } else {
            AuthJourney(modifier = appModifier)
        }
    }
}