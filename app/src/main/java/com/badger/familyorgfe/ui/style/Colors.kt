package com.badger.familyorgfe.ui.style

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun outlinedTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = FamilyOrganizerTheme.colors.blackPrimary,
    unfocusedBorderColor = FamilyOrganizerTheme.colors.blackPrimary,
    cursorColor = FamilyOrganizerTheme.colors.blackPrimary
)

@Composable
fun buttonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FamilyOrganizerTheme.colors.primary,
    disabledBackgroundColor = FamilyOrganizerTheme.colors.darkClay
)