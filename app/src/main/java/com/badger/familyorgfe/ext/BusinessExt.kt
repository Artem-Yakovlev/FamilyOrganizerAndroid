package com.badger.familyorgfe.ext

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter

fun Product.toFridgeItem(): FridgeItem {
    val expDaysLeft = expiryMillis?.let { expiryMillis ->
        val expDate = Instant.ofEpochSecond(expiryMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

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


/**
 * Expiration operations
 * */

private const val ZERO = "0"
private const val DATE_FORMAT = "dd.MM.yyyy"
private val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

fun LocalDate?.toExpirationDays(): String? {
    val expirationDays = try {
        if (LocalDate.now() == this) {
            ZERO
        } else {
            Duration.between(
                LocalDate.now().atStartOfDay(),
                this?.atStartOfDay()
            ).toDays().toString()
        }
    } catch (e: Exception) {
        null
    }
    return expirationDays
}

fun Int.toExpirationDate(): LocalDate = LocalDate.now().plusDays(toLong())

fun LocalDate.toExpirationDateString() = try {
    format(dateFormatter).orEmpty()
} catch (e: Exception) {
    null
}