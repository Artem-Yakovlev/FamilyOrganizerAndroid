package com.badger.familyorgfe.data.source.products.json

import com.badger.familyorgfe.data.model.Product

class GetProductsJson {

    data class Form(
        val familyId: Long,
    )

    data class Response(
        val products: List<Product>
    )
}