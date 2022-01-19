package com.badger.familyorgfe.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//// Set of Material typography styles to start with
//val Typography = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
//    button = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.W500,
//        fontSize = 14.sp,
//        fontFeatureSettings = "c2sc, smcp"
//    ),
//    caption = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp
//    ),
//    h1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Bold,
//        fontSize = 24.sp
//    ),
//    subtitle1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp
//    ),
//)

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