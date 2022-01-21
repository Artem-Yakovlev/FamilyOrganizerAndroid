package com.badger.familyorgfe.data.source.converters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset


object DateTimeConverter {

    @TypeConverter
    fun toDate(millis: Long?): LocalDateTime? {
        return millis?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun fromDate(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }
}