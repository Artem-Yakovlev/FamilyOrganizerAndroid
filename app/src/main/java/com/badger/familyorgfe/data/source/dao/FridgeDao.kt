package com.badger.familyorgfe.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.badger.familyorgfe.data.model.Fridge

@Dao
interface FridgeDao {

    @Query("SELECT * FROM fridges")
    fun getAll(): List<Fridge>

    @Query("SELECT * FROM fridges WHERE id = :id")
    fun loadById(id: String): Fridge

    @Insert
    fun insertAll(vararg fridges: Fridge)

    @Delete
    fun delete(fridge: Fridge)
}