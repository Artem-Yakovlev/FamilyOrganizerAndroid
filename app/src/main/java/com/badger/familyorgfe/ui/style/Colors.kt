package com.badger.familyorgfe.ui.style

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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

@Composable
fun switchColors() = SwitchDefaults.colors(
    checkedTrackColor = FamilyOrganizerTheme.colors.primary,
    checkedThumbColor = FamilyOrganizerTheme.colors.primary,
)

@Composable
fun outlinedButtonColors() = ButtonDefaults.outlinedButtonColors(
    backgroundColor = Color.Transparent,
    contentColor = FamilyOrganizerTheme.colors.primary,
    disabledContentColor = FamilyOrganizerTheme.colors.darkClay
)

@Composable
fun textButtonColors() = ButtonDefaults.textButtonColors(
    backgroundColor = FamilyOrganizerTheme.colors.whitePrimary,
    contentColor = FamilyOrganizerTheme.colors.primary,
    disabledContentColor = FamilyOrganizerTheme.colors.darkClay
)

@Composable
fun checkBoxColors() = CheckboxDefaults.colors(
    checkedColor = FamilyOrganizerTheme.colors.primary
)