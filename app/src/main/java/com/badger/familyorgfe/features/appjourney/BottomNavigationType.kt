package com.badger.familyorgfe.features.appjourney

import com.badger.familyorgfe.R

enum class BottomNavigationType(
    val route: String,
    val resourceId: Int
) {
    FRIDGE(
        route = toString(),
        resourceId = R.drawable.ic_bottom_navigation_fridge),

    ADDING(
        route = toString(),
        resourceId = R.drawable.ic_bottom_navigation_plus),

    PROFILE(
        route = toString(),
        resourceId = R.drawable.ic_bottom_navigation_account)
}
