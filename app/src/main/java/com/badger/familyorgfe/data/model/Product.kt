package com.badger.familyorgfe.data.model

import androidx.compose.ui.graphics.Color
import com.badger.familyorgfe.ui.theme.ExpirationBadSoonColor
import com.badger.familyorgfe.ui.theme.ExpirationNormalColor
import com.badger.familyorgfe.ui.theme.ExpirationSpoiledColor

data class Product(
    val id: Long,
    val name: String,
    val quantity: Double?,
    val measure: Measure?,
    val category: Category,
    val expiryMillis: Long?,
) {
    enum class Measure {
        LITER,
        KILOGRAM,
        THINGS
    }

    enum class Category {
        JUNK,
        HEALTHY,
        DEFAULT,
        OTHER
    }

    enum class ExpirationStatus(val color: Color) {
        NORMAL(color = ExpirationNormalColor),
        BAD_SOON(color = ExpirationBadSoonColor),
        SPOILED(color = ExpirationSpoiledColor)
    }
}