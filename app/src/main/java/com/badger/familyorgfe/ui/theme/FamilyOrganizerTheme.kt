package com.badger.familyorgfe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class FamilyOrganizerColors(
    val whitePrimary: Color,
    val blackPrimary: Color,
    val disabled: Color,

    val primary: Color,
    val darkPrimary: Color,
    val darkGray: Color,
    val lightGray: Color,

    val darkClay: Color,
    val lightClay: Color,
)

data class FamilyOrganizerTextStyle(
    val headline1: TextStyle,
    val headline2: TextStyle,
    val headline3: TextStyle,

    val subtitle1: TextStyle,
    val subtitle2: TextStyle,

    val button: TextStyle,
    val input: TextStyle,
    val body: TextStyle,
    val label: TextStyle
)

object FamilyOrganizerTheme {
    val colors: FamilyOrganizerColors
        @Composable
        get() = LocalFamilyOrganizerColors.current

    val textStyle: FamilyOrganizerTextStyle
        @Composable
        get() = LocalFamilyOrganizerTextStyle.current
}

val LocalFamilyOrganizerColors = staticCompositionLocalOf<FamilyOrganizerColors> {
    error("No colors provided")
}

val LocalFamilyOrganizerTextStyle = staticCompositionLocalOf<FamilyOrganizerTextStyle> {
    error("No fonts provided")
}