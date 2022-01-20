package com.badger.familyorgfe.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val familyOrganizerTextStyle = FamilyOrganizerTextStyle(
    headline1 = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
    headline2 = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    headline3 = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),

    subtitle1 = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Light),
    subtitle2 = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light),

    button = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
    input = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    body = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
)