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
import com.badger.familyorgfe.features.familyauthjourney.FamilyAuthJourney
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: IMainViewModel by viewModels<MainViewModel>()

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
        val auth by viewModel.isAuth.collectAsState()
        val family by viewModel.hasFamily.collectAsState()
        val appModifier = Modifier.fillMaxSize()

        auth?.let { isAuth ->
            family?.let { hasFamily ->
                when {
                    isAuth && hasFamily -> AppJourney(modifier = appModifier)
                    isAuth -> FamilyAuthJourney(modifier = appModifier)
                    else -> AuthJourney(modifier = appModifier)
                }
            }
        }
    }
}