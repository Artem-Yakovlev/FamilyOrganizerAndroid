package com.badger.familyorgfe.features.appjourney.products.scanner.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.GetProductsByCodeJson
import javax.inject.Inject

class GetProductsByCodeUseCase @Inject constructor(
    private val productsApi: ProductsApi
) : BaseUseCase<String, List<Product>>() {

    override suspend fun invoke(arg: String): List<Product> {
        return try {
            val form = GetProductsByCodeJson.Form(code = arg)
            productsApi.getProductsByCode(form).data.products
        } catch (e: Exception) {
            emptyList()
        }
    }
}