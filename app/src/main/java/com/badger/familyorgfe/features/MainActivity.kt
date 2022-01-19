package com.badger.familyorgfe.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.lifecycle.ProcessCameraProvider
import com.badger.familyorgfe.features.auth.AuthScreen
import com.badger.familyorgfe.ui.theme.FamilyOrgFeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProcessCameraProvider.getInstance(this)
        setContent {
            FamilyOrgFeTheme {
                AuthScreen()
            }
        }
    }
}