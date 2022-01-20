package com.badger.familyorgfe.features.appjourney.fridge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun FridgeScreen(modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .background(color = FamilyOrganizerTheme.colors.lightClay)
        ) {

        }
    }
}