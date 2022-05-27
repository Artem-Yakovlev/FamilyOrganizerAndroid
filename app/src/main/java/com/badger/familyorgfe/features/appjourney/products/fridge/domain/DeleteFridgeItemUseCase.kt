package com.badger.familyorgfe.features.appjourney.products.fridge.domain

import com.badger.familyorgfe.base.SyncUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.products.json.DeleteProductsJson
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteFridgeItemUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productsApi: ProductsApi
) : SyncUseCase<FridgeItem, Unit>() {


    override suspend fun invoke(arg: FridgeItem) {
        val familyId = dataStoreRepository.familyId.firstOrNull() ?: return
        val form = DeleteProductsJson.Form(
            familyId = familyId,
            deleteIds = listOf(arg.id)
        )
        try {
            productsApi.deleteProducts(form)
        } catch (e: Exception) {

        }
    }
}