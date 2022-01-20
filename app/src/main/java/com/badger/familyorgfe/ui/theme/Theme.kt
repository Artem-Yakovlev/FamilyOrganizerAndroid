package com.badger.familyorgfe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val lightFamilyOrganizerColors = FamilyOrganizerColors(
    whitePrimary = WhitePrimary,
    blackPrimary = BlackPrimary,
    disabled = Disabled,

    primary = Primary,
    darkPrimary = DarkPrimary,

    lightGray = LightGray,
    darkGray = DarkGray,

    lightClay = LightClay,
    darkClay = DarkClay
)

@Composable
fun FamilyOrganizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = lightFamilyOrganizerColors
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