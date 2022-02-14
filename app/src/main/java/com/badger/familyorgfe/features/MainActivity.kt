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
import com.badger.familyorgfe.navigation.NavigationManager
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: IMainViewModel by viewModels<MainViewModel>()
    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(application)
        ProcessCameraProvider.getInstance(this)
        setContent {
            FamilyOrganizerTheme {
                JourneyScreen()
            }
        }
    }

    @Composable
    private fun JourneyScreen() {
        val isUserAuthorized by viewModel.isUserAuthorized.collectAsState()
        val appModifier = Modifier.fillMaxSize()

        if (isUserAuthorized) {
            AppJourney(modifier = appModifier)
        } else {
            AuthJourney(
                modifier = appModifier,
                lifecycleOwner = this,
                navigationManager = navigationManager
            )
        }
    }
}