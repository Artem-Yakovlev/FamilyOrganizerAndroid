package com.badger.familyorgfe.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    fun getByIds(productIds: List<String>): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: String): Flow<Product>

    @Insert
    suspend fun insertAll(vararg products: Product)

    @Query("DELETE FROM products WHERE id IN (:ids)")
    suspend fun deleteByIds(vararg ids: String)
}