package com.badger.familyorgfe.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class FamilyOrganizerColors(
    val text: Color,
    val disabled: Color,
    val background: Color,
    val backgroundOpposite: Color,

    val lightBorder: Color,
    val selectedWaterBar: Color,
    val backgroundWaterBar: Color,

    val bluePalette: FamilyOrganizerColoredPalette,
    val orangePalette: FamilyOrganizerColoredPalette,
    val greenPalette: FamilyOrganizerColoredPalette
)

data class FamilyOrganizerColoredPalette(
    val text: Color,
    val primary: Color,
    val background: Color,
    val disabled: Color
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