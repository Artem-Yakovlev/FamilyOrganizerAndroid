package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.data.source.family.model.OnlineUser

class GetAllMembersJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val onlineUsers: List<OnlineUser>
    )
}