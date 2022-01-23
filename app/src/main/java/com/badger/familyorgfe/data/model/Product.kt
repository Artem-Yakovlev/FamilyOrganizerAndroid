package com.badger.familyorgfe.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.badger.familyorgfe.ui.theme.ExpirationBadSoonColor
import com.badger.familyorgfe.ui.theme.ExpirationNormalColor
import com.badger.familyorgfe.ui.theme.ExpirationSpoiledColor
import org.threeten.bp.LocalDateTime

@Entity(tableName = "products")
data class Product(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "quantity")
    val quantity: Double?,

    @ColumnInfo(name = "measure")
    val measure: Measure?,

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "expiryDate")
    val expiryDate: LocalDateTime?,

    @ColumnInfo(name = "createdAt")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: LocalDateTime
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