package com.badger.familyorgfe.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.badger.familyorgfe.data.model.LocalName

@Dao
interface LocalNameDao {

    @Query("SELECT * FROM local_names")
    suspend fun getAll(): List<LocalName>

    @Query("SELECT * FROM local_names WHERE email = :email")
    suspend fun getLocalNameByEmail(email: String) : LocalName?

    @Insert
    suspend fun insertAll(vararg localName: LocalName)

    @Query("DELETE FROM local_names WHERE email IN (:emails)")
    suspend fun deleteByEmail(vararg emails: String)
}