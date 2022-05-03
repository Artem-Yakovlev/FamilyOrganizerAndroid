package com.badger.familyorgfe.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun FullScreenLoading(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.2f))
                .alpha(alpha = 0.2f),
            content = {}
        )
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = FamilyOrganizerTheme.colors.darkClay
        )
    }
}