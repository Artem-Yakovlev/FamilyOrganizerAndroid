package com.badger.familyorgfe.data.source.user.json

class UpdateStatusJson {
    data class Form(
        val status: String
    )

    data class Response(
        val success: Boolean
    )
}