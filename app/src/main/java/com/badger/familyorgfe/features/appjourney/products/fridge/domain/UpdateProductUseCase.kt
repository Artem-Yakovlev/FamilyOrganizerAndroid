package com.badger.familyorgfe.features.appjourney.products.fridge.domain

import com.badger.familyorgfe.base.SyncUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.UpdateProductsJson
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productsApi: ProductsApi
) : SyncUseCase<Product, Boolean>() {


    override suspend fun invoke(arg: Product): Boolean {
        val familyId = dataStoreRepository.familyId.firstOrNull() ?: return false
        val form = UpdateProductsJson.Form(
            familyId = familyId,
            product = arg
        )
        return try {
            productsApi.updateProducts(form).data.success
        } catch (e: Exception) {
            false
        }
    }
}