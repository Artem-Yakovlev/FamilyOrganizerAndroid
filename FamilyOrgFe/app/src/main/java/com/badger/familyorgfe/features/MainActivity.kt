package com.badger.familyorgfe.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.badger.familyorgfe.ui.theme.FamilyOrgFeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProcessCameraProvider.getInstance(this)
        setContent {
            FamilyOrgFeTheme {
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }
}