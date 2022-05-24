package com.badger.familyorgfe.data.source.products.json

import com.badger.familyorgfe.data.model.Product

class GetProductsByCodeJson {

    data class Form(
        val code: String,
    )

    data class Response(
        val products: List<Product>
    )
}