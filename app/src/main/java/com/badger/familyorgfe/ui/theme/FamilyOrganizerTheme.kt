package com.badger.familyorgfe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class FamilyOrganizerColors(
    val whitePrimary : Color,
    val blackPrimary : Color,
    val disabled : Color,

    val primary : Color,
    val darkPrimary : Color,
    val darkGray : Color,
    val lightGray : Color,

    val darkClay : Color,
    val lightClay : Color,
)

data class FamilyOrganizerTextStyle(
    val boldHeading: TextStyle,
    val boldTitle: TextStyle,
    val boldBody: TextStyle,

    val primaryHeading: TextStyle,
    val primaryTitle: TextStyle,
    val primaryBody: TextStyle,

    val secondaryHeading: TextStyle,
    val secondaryTitle: TextStyle,
    val secondaryBody: TextStyle,
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