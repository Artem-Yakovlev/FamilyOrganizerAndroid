package com.badger.familyorgfe.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.badger.familyorgfe.data.model.Fridge
import kotlinx.coroutines.flow.Flow

@Dao
interface FridgeDao {

    @Query("SELECT * FROM fridges")
    fun getAll(): Flow<List<Fridge>>

    @Query("SELECT * FROM fridges WHERE id = :id")
    fun getById(id: String): Flow<Fridge>

    @Insert
    suspend fun insertAll(vararg fridges: Fridge)
}