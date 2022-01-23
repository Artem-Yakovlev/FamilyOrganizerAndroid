package com.badger.familyorgfe.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.badger.familyorgfe.data.model.Fridge
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.converters.DateTimeConverter
import com.badger.familyorgfe.data.source.converters.StringListConverter
import com.badger.familyorgfe.data.source.dao.FridgeDao
import com.badger.familyorgfe.data.source.dao.ProductDao
import com.badger.familyorgfe.data.source.dao.UserDao

@TypeConverters(DateTimeConverter::class, StringListConverter::class)
@Database(entities = [User::class, Fridge::class, Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun fridgeDao(): FridgeDao
    abstract fun productDao(): ProductDao

}