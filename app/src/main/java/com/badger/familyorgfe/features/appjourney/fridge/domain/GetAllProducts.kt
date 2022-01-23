package com.badger.familyorgfe.features.appjourney.fridge.domain

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.repository.IProductRepository
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFridgeItems @Inject constructor(
    private val repository: IProductRepository
) : FlowUseCase<Unit, List<FridgeItem>>() {

    override fun invoke(arg: Unit): Flow<List<FridgeItem>> {
        return repository.getAllProducts().map { list -> list.toFridgeItems() }
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