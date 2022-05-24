package com.badger.familyorgfe.features.appjourney.fridge.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.GetProductsJson
import com.badger.familyorgfe.ext.toFridgeItem
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetAllFridgeItemsUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productsApi: ProductsApi
) : BaseUseCase<Unit, List<FridgeItem>>() {

    override suspend fun invoke(arg: Unit): List<FridgeItem> {
        val familyId = dataStoreRepository.familyId.firstOrNull() ?: return emptyList()
        return try {
            productsApi.getProducts(
                GetProductsJson.Form(familyId)
            ).data.products
        } catch (e: Exception) {
            emptyList()
        }.map { product -> product.toFridgeItem() }

    }
}