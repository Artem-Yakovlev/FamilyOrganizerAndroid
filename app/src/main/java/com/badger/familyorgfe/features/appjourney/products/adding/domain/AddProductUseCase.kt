package com.badger.familyorgfe.features.appjourney.products.adding.domain

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
) : BaseUseCase<AddProductUseCase.Argument, Boolean>() {

    data class Argument(
        val products: List<Product>,
        val tasks: List<Long>
    )

    override suspend fun invoke(arg: Argument): Boolean {
        val familyId = dataStoreRepository.familyId.firstOrNull() ?: return false
        val form = AddProductsJson.Form(
            familyId = familyId,
            products = arg.products,
            tasks = arg.tasks
        )
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