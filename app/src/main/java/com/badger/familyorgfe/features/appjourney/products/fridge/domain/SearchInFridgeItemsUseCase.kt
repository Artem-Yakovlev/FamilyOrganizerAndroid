package com.badger.familyorgfe.features.appjourney.products.fridge.domain

import com.badger.familyorgfe.base.SyncUseCase
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeItem
import javax.inject.Inject

class SearchInFridgeItemsUseCase @Inject constructor(
) : SyncUseCase<Pair<List<FridgeItem>, String>, List<FridgeItem>>() {

    override suspend fun invoke(arg: Pair<List<FridgeItem>, String>): List<FridgeItem> {
        val (items, search) = arg
        return items.filter { item -> item.name.startsWith(search) }
    }
}