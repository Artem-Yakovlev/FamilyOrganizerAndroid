package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getProductByIds(ids: List<String>): Flow<List<Product>>

    suspend fun deleteItemByIds(ids: List<String>)
}