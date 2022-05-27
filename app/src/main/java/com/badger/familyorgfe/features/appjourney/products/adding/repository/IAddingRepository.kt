package com.badger.familyorgfe.features.appjourney.products.adding.repository

import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.Flow

interface IAddingRepository {

    val readyToAddingProducts: Flow<List<Product>>

    fun addProducts(vararg product: Product)

    fun deleteProductById(id: Long)

    fun updateProduct(product: Product)

    fun clearStorage()
}