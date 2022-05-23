package com.badger.familyorgfe.data.source.user.json

import com.badger.familyorgfe.data.model.User

class GetProfileJson {

    class Form

    data class Response(
        val user: User?
    )
}
