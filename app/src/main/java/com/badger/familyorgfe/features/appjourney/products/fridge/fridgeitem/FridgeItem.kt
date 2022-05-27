package com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem

import com.badger.familyorgfe.data.model.Product


data class FridgeItem(
    val id: Long,
    val name: String,
    val quantity: Double?,
    val measure: Product.Measure?,
    val category: Product.Category,
    val expDaysLeft: Int?,
    val expStatus: Product.ExpirationStatus?
) {
    val hasMeasureAndQuantity get() = measure != null && quantity != null

    val hasExpiration get() = expStatus != null && expDaysLeft != null

    companion object {

        fun mock(id: Long) = FridgeItem(
            id = id,
            name = "Шоколадный батончик",
            quantity = 1.0,
            measure = Product.Measure.THINGS,
            category = Product.Category.HEALTHY,
            expDaysLeft = 5,
            expStatus = Product.ExpirationStatus.BAD_SOON
        )
    }
}
