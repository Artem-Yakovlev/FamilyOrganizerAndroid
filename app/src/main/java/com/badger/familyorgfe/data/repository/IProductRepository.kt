package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllProducts(): Flow<List<Product>>
}