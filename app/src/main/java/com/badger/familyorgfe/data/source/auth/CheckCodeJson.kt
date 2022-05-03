package com.badger.familyorgfe.data.source.auth

class CheckCodeJson {

    data class Form(
        val email: String,
        val code: String
    )

    data class Response(
        val email: String,
        val success: Boolean,
        val token: String?
    )

}