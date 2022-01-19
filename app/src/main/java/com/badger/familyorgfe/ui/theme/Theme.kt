package com.badger.familyorgfe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val blueColorBrightPalette = FamilyOrganizerColoredPalette(
    text = DarkBlue,
    primary = PrimaryBlue,
    background = LightBlue,
    disabled = DisabledBlue
)

private val orangeColorBrightPalette = FamilyOrganizerColoredPalette(
    text = DarkOrange,
    primary = PrimaryOrange,
    background = LightOrange,
    disabled = DisabledOrange
)

private val greenColorBrightPalette = FamilyOrganizerColoredPalette(
    text = DarkGreen,
    primary = PrimaryGreen,
    background = LightGreen,
    disabled = DisabledGreen
)

private val lightFamilyOrganizerColors = FamilyOrganizerColors(
    text = BlackPrimary,
    disabled = Disabled,
    background = WhitePrimary,
    backgroundOpposite = BlackPrimary,

    lightBorder = LightBorder,
    selectedWaterBar = WaterBar,
    backgroundWaterBar = BackgroundWaterBar,

    bluePalette = blueColorBrightPalette,
    orangePalette = orangeColorBrightPalette,
    greenPalette = greenColorBrightPalette
)

private val darkFamilyOrganizerColors = FamilyOrganizerColors(
    text = WhitePrimary,
    disabled = Disabled,
    background = BlackPrimary,
    backgroundOpposite = WhitePrimary,

    lightBorder = LightBorder,
    selectedWaterBar = WaterBar,
    backgroundWaterBar = BackgroundWaterBar,

    bluePalette = blueColorBrightPalette,
    orangePalette = orangeColorBrightPalette,
    greenPalette = greenColorBrightPalette
)

@Composable
fun FamilyOrgFeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        lightFamilyOrganizerColors
    } else {
        darkFamilyOrganizerColors
    }
    val textStyle = familyOrganizerTextStyle

    CompositionLocalProvider(
        // shapes
        LocalFamilyOrganizerColors provides colors,
        LocalFamilyOrganizerTextStyle provides textStyle,
        content = {
            content()
        }
    )
}