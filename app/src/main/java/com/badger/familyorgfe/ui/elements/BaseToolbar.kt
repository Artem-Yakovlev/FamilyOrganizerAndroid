package com.badger.familyorgfe.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun BaseToolbar(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(color = FamilyOrganizerTheme.colors.lightClay)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.Bottom,
        content = content
    )
}