package com.badger.familyorgfe.data.source.products.json

import com.badger.familyorgfe.data.model.Product

class AddProductsJson {

    data class Form(
        val familyId: Long,
        val products: List<Product>
    )

    data class Response(
        val products: List<Product>
    )
}