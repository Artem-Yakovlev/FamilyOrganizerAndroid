package com.badger.familyorgfe.features.appjourney.products.adding.repository

import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow

class AddingRepository : IAddingRepository {

    override val readyToAddingProducts: MutableStateFlow<List<Product>> = MutableStateFlow(
        emptyList()
    )

    override fun addProducts(vararg product: Product) {
        readyToAddingProducts.value = (readyToAddingProducts.value + product)
            .sortedBy(Product::name)
    }

    override fun deleteProductById(id: Long) {
        readyToAddingProducts.value = readyToAddingProducts.value.filter { it.id != id }
    }

    override fun updateProduct(product: Product) {
        readyToAddingProducts.value = readyToAddingProducts.value.map { item ->
            if (item.id == product.id) {
                product
            } else {
                item
            }
        }
    }

    override fun clearStorage() {
        readyToAddingProducts.value = emptyList()
    }
}