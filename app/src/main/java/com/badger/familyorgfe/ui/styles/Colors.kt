package com.badger.familyorgfe.ui.styles

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.ui.theme.WhitePrimary

@Composable
fun blueTextFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = WhitePrimary,
    focusedIndicatorColor = FamilyOrganizerTheme.colors.bluePalette.primary,
    focusedLabelColor = FamilyOrganizerTheme.colors.bluePalette.primary,
    cursorColor = FamilyOrganizerTheme.colors.bluePalette.primary
)

@Composable
fun blueButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FamilyOrganizerTheme.colors.bluePalette.primary,
    disabledBackgroundColor = FamilyOrganizerTheme.colors.disabled,
)

@Composable
fun blueRadioButtonColors() = RadioButtonDefaults.colors(
    selectedColor = FamilyOrganizerTheme.colors.bluePalette.primary
)

@Composable
fun greenTextFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = WhitePrimary,
    focusedIndicatorColor = FamilyOrganizerTheme.colors.greenPalette.primary,
    focusedLabelColor = FamilyOrganizerTheme.colors.greenPalette.primary,
    cursorColor = FamilyOrganizerTheme.colors.greenPalette.primary
)

@Composable
fun greenButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FamilyOrganizerTheme.colors.greenPalette.primary,
    disabledBackgroundColor = FamilyOrganizerTheme.colors.disabled,
)

@Composable
fun greenRadioButtonColors() = RadioButtonDefaults.colors(
    selectedColor = FamilyOrganizerTheme.colors.greenPalette.primary
)

@Composable
fun orangeTextFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = WhitePrimary,
    focusedIndicatorColor = FamilyOrganizerTheme.colors.orangePalette.primary,
    focusedLabelColor = FamilyOrganizerTheme.colors.orangePalette.primary,
    cursorColor = FamilyOrganizerTheme.colors.orangePalette.primary
)

@Composable
fun orangeButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FamilyOrganizerTheme.colors.orangePalette.primary,
    disabledBackgroundColor = FamilyOrganizerTheme.colors.disabled,
)

@Composable
fun orangeRadioButtonColors() = RadioButtonDefaults.colors(
    selectedColor = FamilyOrganizerTheme.colors.orangePalette.primary
)

@Composable
fun oppositeButtonColors() = ButtonDefaults.buttonColors(
    backgroundColor = FamilyOrganizerTheme.colors.backgroundOpposite
)