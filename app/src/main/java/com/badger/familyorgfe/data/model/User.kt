package com.badger.familyorgfe.data.model

import androidx.compose.ui.graphics.Color
import com.badger.familyorgfe.R
import com.badger.familyorgfe.di.BASE_URL
import com.badger.familyorgfe.ui.theme.*

data class User(
    val email: String,
    val name: String,
    private val imagePath: String?,
    val status: UserStatus
) {

    fun getImageUrl() = "$BASE_URL$imagePath"

    companion object {
        fun createEmpty() = User(
            name = "",
            email = "",
            imagePath = "",
            status = UserStatus.UNDEFINED
        )
    }
}

enum class UserStatus(val stringResource: Int, val color: Color) {
    UNDEFINED(stringResource = R.string.status_undefined, color = StatusUndefinedColor),
    AT_HOME(stringResource = R.string.status_at_home, color = StatusAtHomeColor),
    AT_SHOP(stringResource = R.string.status_at_shop, color = StatusAtShopColor),
    AT_WALK(stringResource = R.string.status_at_walk, color = StatusAtWalkColor),
    AT_WORK(stringResource = R.string.status_at_work, color = StatusAtWorkColor),
    IN_ROAD(stringResource = R.string.status_in_road, color = StatusInRoadColor)
}