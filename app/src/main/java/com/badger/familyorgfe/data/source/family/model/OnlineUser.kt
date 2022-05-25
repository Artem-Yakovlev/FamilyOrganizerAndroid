package com.badger.familyorgfe.data.source.family.model

import com.badger.familyorgfe.data.model.User

data class OnlineUser(
    val user: User,
    val lastRegisterTime: Long
) {
    val name1 = user.name
    val email = user.email

    companion object {
        fun createEmpty() = OnlineUser(
            user = User.createEmpty(),
            lastRegisterTime = 0L
        )
    }
}