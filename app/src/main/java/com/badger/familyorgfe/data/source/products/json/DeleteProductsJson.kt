package com.badger.familyorgfe.data.source.products.json

import com.badger.familyorgfe.data.model.Product

class DeleteProductsJson {

    data class Form(
        val familyId: Long,
        val deleteIds: List<Long>
    )

    data class Response(
        val products: List<Product>
    )
}