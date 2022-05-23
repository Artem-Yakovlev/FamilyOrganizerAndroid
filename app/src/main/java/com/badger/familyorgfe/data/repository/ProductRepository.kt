package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.products.ProductsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsApi: ProductsApi,
    database: AppDatabase
) : IProductRepository {

    private val productDao = database.productDao()

    override fun getProductByIds(ids: List<String>): Flow<List<Product>> {
        return productDao.getByIds(ids)
    }

    override suspend fun deleteItemByIds(ids: List<String>) {
        productDao.deleteByIds(ids = ids.toTypedArray())
    }
}