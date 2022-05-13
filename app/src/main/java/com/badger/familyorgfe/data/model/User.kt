package com.badger.familyorgfe.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "name")
    val name: String
) {

    companion object {
        fun createEmpty() = User(
            name = "",
            email = ""
        )
    }
}