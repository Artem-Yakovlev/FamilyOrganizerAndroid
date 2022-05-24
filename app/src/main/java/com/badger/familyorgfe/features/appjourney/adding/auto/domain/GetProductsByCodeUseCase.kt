package com.badger.familyorgfe.features.appjourney.adding.auto.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetProductsByCodeUseCase @Inject constructor() : BaseUseCase<String, List<Product>>() {

    override suspend fun invoke(arg: String): List<Product> {
        delay(2500)
        return emptyList()
    }
}