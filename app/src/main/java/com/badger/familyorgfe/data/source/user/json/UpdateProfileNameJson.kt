package com.badger.familyorgfe.data.source.user.json

import com.badger.familyorgfe.data.model.User

class UpdateProfileNameJson {

    class Form(
        val updatedName: String
    )

    class Response(
        val user: User
    )
}