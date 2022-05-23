package com.badger.familyorgfe.features.appjourney.fridge.domain

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.GetProductsJson
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFridgeItemsUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productsApi: ProductsApi
) : FlowUseCase<Unit, List<FridgeItem>>() {

    override fun invoke(arg: Unit): Flow<List<FridgeItem>> {
        return dataStoreRepository.familyId.filterNotNull()
            .map { familyId ->
                try {
                    productsApi.getProducts(
                        GetProductsJson.Form(familyId)
                    ).data.products
                } catch (e: Exception) {
                    emptyList()
                }
            }.map { products -> products.toFridgeItems() }
    }

    private fun List<Product>.toFridgeItems() = map { item -> item.toFridgeItem() }

    private fun Product.toFridgeItem() = FridgeItem(
        id = id,
        name = name,
        quantity = quantity,
        measure = measure,
        category = category,
        expDaysLeft = 5,
        expStatus = Product.ExpirationStatus.BAD_SOON
    )
}