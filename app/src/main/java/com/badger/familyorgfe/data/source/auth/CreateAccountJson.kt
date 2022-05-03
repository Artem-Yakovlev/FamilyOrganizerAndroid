package com.badger.familyorgfe.data.source.auth

class CreateAccountJson {

    data class Form(
        val email: String
    )

    class Response(
        val success: Boolean
    )
}