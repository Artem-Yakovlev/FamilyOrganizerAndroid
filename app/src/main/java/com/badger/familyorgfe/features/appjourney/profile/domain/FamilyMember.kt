package com.badger.familyorgfe.features.appjourney.profile.domain

import androidx.compose.ui.graphics.Color
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.ui.theme.*

data class FamilyMember(
    val name: String,
    val status: Status,
    val online: Boolean,
    val user: User
) {

    enum class Status(val stringResource: Int, val color: Color) {
        UNDEFINED(stringResource = R.string.status_undefined, color = StatusUndefinedColor),
        AT_HOME(stringResource = R.string.status_at_home, color = StatusAtHomeColor),
        AT_SHOP(stringResource = R.string.status_at_shop, color = StatusAtShopColor),
        AT_WALK(stringResource = R.string.status_at_walk, color = StatusAtWalkColor),
        AT_WORK(stringResource = R.string.status_at_work, color = StatusAtWorkColor),
        IN_ROAD(stringResource = R.string.status_in_road, color = StatusInRoadColor)
    }

    companion object {
        fun createEmpty() = FamilyMember(
            name = "",
            status = Status.AT_HOME,
            online = false,
            user = User.createEmpty()
        )

        fun createForMainUser(user: User) = FamilyMember(
            name = user.name,
            status = Status.AT_HOME,
            online = true,
            user = user
        )
    }
}