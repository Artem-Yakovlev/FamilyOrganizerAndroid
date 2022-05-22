package com.badger.familyorgfe.ext

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

fun Product.toFridgeItem(): FridgeItem {
    val expDaysLeft = expiryDate?.let { expDate ->
        if (LocalDateTime.now() == expDate) {
            0
        } else {
            Duration.between(
                LocalDateTime.now(),
                expDate
            ).toDays() + 1
        }
    }
    return FridgeItem(
        id = id,
        name = name,
        quantity = quantity,
        measure = measure,
        category = category,
        expDaysLeft = expDaysLeft?.toInt(),
        expStatus = expDaysLeft?.toInt()?.let(::getExpirationStatusFor)
    )
}

fun getExpirationStatusFor(expDaysLeft: Int) = when {
    expDaysLeft < 0 -> Product.ExpirationStatus.SPOILED
    expDaysLeft <= 3 -> Product.ExpirationStatus.BAD_SOON
    else -> Product.ExpirationStatus.NORMAL
}