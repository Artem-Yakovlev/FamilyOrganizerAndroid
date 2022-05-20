package com.badger.familyorgfe.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.theme.*

@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "status")
    val status: UserStatus
) {

    companion object {
        fun createEmpty() = User(
            name = "",
            email = "",
            imageUrl = "",
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