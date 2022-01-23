package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.ProductApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi,
    database: AppDatabase
) : IProductRepository {

    private val fridgeDao = database.fridgeDao()

    private val productDao = database.productDao()

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAll()
    }
}