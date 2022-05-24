package com.badger.familyorgfe.data.source.products.json

import com.badger.familyorgfe.data.model.Product

class UpdateProductsJson {

    data class Form(
        val familyId: Long,
        val product: Product
    )

    data class Response(
        val success: Boolean
    )
}