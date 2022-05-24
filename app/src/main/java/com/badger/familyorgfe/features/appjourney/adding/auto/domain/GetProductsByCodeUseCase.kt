package com.badger.familyorgfe.features.appjourney.adding.auto.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class GetProductsByCodeUseCase @Inject constructor() : BaseUseCase<String, List<Product>>() {

    override suspend fun invoke(arg: String): List<Product> {
        delay(2500)
        return listOf(
            Product(
                id = Random.nextLong(),
                name = "Hleb",
                quantity = null,
                measure = null,
                category = Product.Category.DEFAULT,
                expiryMillis = null
            )
        )
    }
}