package com.badger.familyorgfe.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeItem
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

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

/**
 * Image
 * */

private const val FILE_NAME = "filename"

fun Uri.toImageFile(context: Context) : File {
    val bitmap = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, this)

    } else {
        ImageDecoder.decodeBitmap(
            ImageDecoder
                .createSource(context.contentResolver, this)
        )
    }

    val file = File(context.cacheDir, FILE_NAME)
    file.createNewFile()

    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    val bitmapData: ByteArray = bos.toByteArray()

    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    try {
        fos?.write(bitmapData)
        fos?.flush()
        fos?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return file
}
