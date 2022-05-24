package com.badger.familyorgfe.features.appjourney.common.productbottomsheet

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.isValidProductName
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import kotlin.random.Random

data class ProductBottomSheetState(
    val title: String,
    val quantity: Double?,
    val measure: Product.Measure,
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
            id = Random.nextLong(),
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
            title = "",
            quantity = null,
            measure = Product.Measure.KILOGRAM,
            expirationDate = null,
            expirationDateString = null,
            expirationDaysString = null
        )
    }
}