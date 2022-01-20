package com.badger.familyorgfe.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val familyOrganizerTextStyle = FamilyOrganizerTextStyle(
    boldHeading = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    boldTitle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
    boldBody = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),

    primaryHeading = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Normal),
    primaryTitle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),
    primaryBody = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),

    secondaryHeading = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Normal, color = LightGray),
    secondaryTitle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal, color = LightGray),
    secondaryBody = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = LightGray),
)