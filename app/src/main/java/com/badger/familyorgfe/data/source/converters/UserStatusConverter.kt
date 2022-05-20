package com.badger.familyorgfe.data.source.converters

import androidx.room.TypeConverter
import com.badger.familyorgfe.data.model.UserStatus

object UserStatusConverter {

    @TypeConverter
    fun toHealth(value: String) = try {
        UserStatus.valueOf(value)
    } catch (e: Exception) {
        UserStatus.UNDEFINED
    }

    @TypeConverter
    fun fromHealth(value: UserStatus) = value.name
}