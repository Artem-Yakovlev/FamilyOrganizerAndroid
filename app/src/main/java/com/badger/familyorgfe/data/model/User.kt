package com.badger.familyorgfe.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "fridge_id")
    val fridgeId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "update_at")
    val updateAt: LocalDateTime,

    @ColumnInfo(name = "is_registered")
    val isRegistered: Boolean
)