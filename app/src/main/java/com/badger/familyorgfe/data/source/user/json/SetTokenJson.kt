package com.badger.familyorgfe.data.source.user.json

class SetTokenJson {

    data class Form(
        val token: String
    )

    data class Response(
        val success: Boolean
    )
}