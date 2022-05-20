package com.badger.familyorgfe.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.badger.familyorgfe.data.model.LocalName
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalNameDao {

    @Query("SELECT * FROM local_names")
    fun getAll(): Flow<List<LocalName>>

    @Query("SELECT * FROM local_names WHERE email = :email")
    suspend fun getLocalNameByEmail(email: String) : LocalName?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg localName: LocalName)

    @Query("DELETE FROM local_names WHERE email IN (:emails)")
    suspend fun deleteByEmail(vararg emails: String)
}