package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.Fridge
import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getFridgeById(id: String): Flow<Fridge>

    fun getProductByIds(ids: List<String>): Flow<List<Product>>
}