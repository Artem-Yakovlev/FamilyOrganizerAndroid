package com.badger.familyorgfe.ui.styles

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun searchTextFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = FamilyOrganizerTheme.colors.lightClay,

    focusedIndicatorColor = FamilyOrganizerTheme.colors.lightClay,
    disabledIndicatorColor = FamilyOrganizerTheme.colors.lightClay,
    errorIndicatorColor = FamilyOrganizerTheme.colors.lightClay,
    unfocusedIndicatorColor = FamilyOrganizerTheme.colors.lightClay,

    cursorColor = FamilyOrganizerTheme.colors.blackPrimary
)