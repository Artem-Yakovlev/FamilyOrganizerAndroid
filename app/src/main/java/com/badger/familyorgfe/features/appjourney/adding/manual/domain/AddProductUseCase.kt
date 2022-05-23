package com.badger.familyorgfe.features.appjourney.adding.manual.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.AddProductsJson
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productsApi: ProductsApi
) : BaseUseCase<List<Product>, Boolean>() {

    override suspend fun invoke(arg: List<Product>): Boolean {
        val familyId = dataStoreRepository.familyId.firstOrNull() ?: return false
        val form = AddProductsJson.Form(familyId = familyId, products = arg)
        return try {
            val response = productsApi.addProducts(form)
            if (response.hasNoErrors()) {
                response.data.products
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}