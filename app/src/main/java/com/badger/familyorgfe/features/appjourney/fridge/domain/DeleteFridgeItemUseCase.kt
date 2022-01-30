package com.badger.familyorgfe.features.appjourney.fridge.domain

import com.badger.familyorgfe.base.SyncUseCase
import com.badger.familyorgfe.data.repository.IProductRepository
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import javax.inject.Inject

class DeleteFridgeItemUseCase @Inject constructor(
    private val productRepository: IProductRepository,
) : SyncUseCase<FridgeItem, Unit>() {


    override suspend fun invoke(arg: FridgeItem) {
        productRepository.deleteItemByIds(listOf(arg.id))
    }
}