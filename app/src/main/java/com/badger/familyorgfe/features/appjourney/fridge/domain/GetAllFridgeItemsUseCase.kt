package com.badger.familyorgfe.features.appjourney.fridge.domain

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.repository.IProductRepository
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFridgeItemsUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val productRepository: IProductRepository,
    private val userRepository: IUserRepository,
) : FlowUseCase<Unit, List<FridgeItem>>() {

    override fun invoke(arg: Unit): Flow<List<FridgeItem>> {
        return dataStoreRepository.userId
            .flatMapLatest(userRepository::getUserByEmail)
            .map { user -> user.fridgeId }
            .flatMapLatest(productRepository::getFridgeById)
            .map { fridge -> fridge.items }
            .flatMapLatest(productRepository::getProductByIds)
            .map { products -> products.toFridgeItems() }
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