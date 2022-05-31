package com.badger.familyorgfe.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.textButtonColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun BaseActionButton(
    onAction: () -> Unit,
    text: String,
    enabled: Boolean
) {
    Button(
        onClick = onAction,
        enabled = enabled,
        colors = buttonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text.uppercase(),
            color = FamilyOrganizerTheme.colors.whitePrimary,
            style = FamilyOrganizerTheme.textStyle.button,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
fun BaseTextButton(
    onAction: () -> Unit,
    text: String,
    enabled: Boolean
) {
    TextButton(
        onClick = onAction,
        enabled = enabled,
        colors = textButtonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text.uppercase(),
            color = FamilyOrganizerTheme.colors.primary,
            style = FamilyOrganizerTheme.textStyle.button,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}