package com.badger.familyorgfe.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.converters.DateTimeConverter
import com.badger.familyorgfe.data.source.converters.StringListConverter
import com.badger.familyorgfe.data.source.converters.UserStatusConverter
import com.badger.familyorgfe.data.source.dao.LocalNameDao
import com.badger.familyorgfe.data.source.dao.UserDao

@TypeConverters(DateTimeConverter::class, StringListConverter::class, UserStatusConverter::class)
@Database(entities = [User::class, LocalName::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun localNamesDao(): LocalNameDao
}