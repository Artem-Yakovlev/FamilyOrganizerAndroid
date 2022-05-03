package com.badger.familyorgfe.data.source.auth

class SendCodeJson {

    data class Form(
        val email: String
    )

    data class Response(
        val success: Boolean
    )

}

