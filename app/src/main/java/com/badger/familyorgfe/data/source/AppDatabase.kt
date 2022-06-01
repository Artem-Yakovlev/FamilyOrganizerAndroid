package com.badger.familyorgfe.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.source.converters.DateTimeConverter
import com.badger.familyorgfe.data.source.converters.StringListConverter
import com.badger.familyorgfe.data.source.converters.UserStatusConverter
import com.badger.familyorgfe.data.source.dao.LocalNameDao

@TypeConverters(DateTimeConverter::class, StringListConverter::class, UserStatusConverter::class)
@Database(entities = [LocalName::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localNamesDao(): LocalNameDao
}