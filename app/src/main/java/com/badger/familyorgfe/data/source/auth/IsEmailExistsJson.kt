package com.badger.familyorgfe.data.source.auth

class IsEmailExistsJson {

    data class Form(
        val email: String
    )

    data class Response(
        val email: String,
        val isExists: Boolean
    )
}

