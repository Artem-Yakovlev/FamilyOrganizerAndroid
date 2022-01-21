package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.source.ProductApi
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) : IProductRepository {
}