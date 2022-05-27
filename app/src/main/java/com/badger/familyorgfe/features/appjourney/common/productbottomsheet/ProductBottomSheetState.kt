package com.badger.familyorgfe.features.appjourney.common.productbottomsheet

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.isValidProductName
import com.badger.familyorgfe.ext.toExpirationDate
import com.badger.familyorgfe.ext.toExpirationDateString
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeItem
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import kotlin.random.Random

data class ProductBottomSheetState(
    val id: Long?,
    val title: String,
    val quantity: Double?,
    val measure: Product.Measure?,
    val expirationDateString: String?,
    val expirationDaysString: String?,
    val expirationDate: LocalDate?
) {
    val createEnabled = title.isValidProductName() && (expirationDate != null
            || expirationDateString.isNullOrEmpty() && expirationDaysString.isNullOrEmpty())

    val isDateError = (expirationDateString != null || expirationDaysString != null)
            && expirationDate == null

    fun createProduct() = if (createEnabled) {
        Product(
            id = id ?: Random.nextLong(),
            name = title,
            quantity = quantity,
            measure = measure,
            category = Product.Category.DEFAULT,
            expiryMillis = expirationDate?.atStartOfDay()?.toEpochSecond(ZoneOffset.UTC)
        )
    } else {
        null
    }

    companion object {
        fun createEmpty() = ProductBottomSheetState(
            id = null,
            title = "",
            quantity = null,
            measure = Product.Measure.KILOGRAM,
            expirationDate = null,
            expirationDateString = null,
            expirationDaysString = null
        )

        fun createFromFridgeItem(item: FridgeItem): ProductBottomSheetState {
            val expirationDate = item.expDaysLeft?.toExpirationDate()

            return ProductBottomSheetState(
                id = item.id,
                title = item.name,
                quantity = item.quantity,
                measure = item.measure,
                expirationDate = expirationDate,
                expirationDaysString = item.expDaysLeft?.toString(),
                expirationDateString = expirationDate?.toExpirationDateString()
            )

        }
    }
}