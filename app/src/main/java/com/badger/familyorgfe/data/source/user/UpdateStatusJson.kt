package com.badger.familyorgfe.data.source.user

class UpdateStatusJson {
    data class Form(
        val status: String
    )

    data class Response(
        val success: Boolean
    )
}