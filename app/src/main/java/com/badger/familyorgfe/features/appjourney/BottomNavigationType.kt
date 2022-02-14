package com.badger.familyorgfe.features.appjourney

import com.badger.familyorgfe.R

enum class BottomNavigationType(
    val resourceId: Int
) {

    FRIDGE(
        resourceId = R.drawable.ic_bottom_navigation_fridge
    ),

    ADDING(
        resourceId = R.drawable.ic_bottom_navigation_plus
    ),

    PROFILE(
        resourceId = R.drawable.ic_bottom_navigation_account
    );

}
