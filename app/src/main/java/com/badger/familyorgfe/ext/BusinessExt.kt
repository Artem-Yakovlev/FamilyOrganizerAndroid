package com.badger.familyorgfe.ext

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem

fun Product.toFridgeItem(): FridgeItem {

    return FridgeItem(
        id = id,
        name = name,
        quantity = quantity,
        measure = measure,
        category = category,
        expDaysLeft = 5,
        expStatus = Product.ExpirationStatus.BAD_SOON
    )
}